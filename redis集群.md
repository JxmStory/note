
# 前言
Redis 单机版安装部署参考文章 ：
 [Linux安装部署Redis(超级详细)](https://www.cnblogs.com/AllWjw/p/15771097.html)
[超详细linux安装redis教程](https://blog.csdn.net/jingttkx/article/details/109098038)

本次集群使用redis版本6.2.6 [官网下载地址](https://redis.io/download/)

集群模式部署配置参考文章：
[Linux安装redis集群](https://blog.csdn.net/Guozr7/article/details/121532715)
[redis6.2.6 集群部署](https://blog.csdn.net/weixin_45551608/article/details/120948104)

---


# 一、部署方式

 1. 使用一台linux服务器模拟集群配置，即同一IP不同端口号实现，端口号为6380~6386六个。
 2. redis安装目录为  **/usr/local/redis/** 
 3. 需先在 **/usr/local** 目录下创建redis文件夹

```powershell
cd /usr/local/
mkdir redis
cd redis
```

# 二、单机部署

 1. 将下载的压缩包 ==redis-6.2.6.tar.gz== 上传至 **/usr/local/redis** 目录下并解压。
 2. 解压完进入 **redis-6.2.6** 目录进行编译、安装。
```powershell
tar -zxvf redis-6.2.6.tar.gz
cd redis-6.2.6
# 安装编译环境
yum install gcc-c++
# 这里如果编译make出可以参考片头文章，下面命令可以升级gcc版本
# yum -y install centos-release-scl && yum -y install devtoolset-9-gcc devtoolset-9-gcc-c++ devtoolset-9-binutils && scl enable devtoolset-9 bash
make
# 安装redis
make PREFIX=/usr/local/redis/redis-6.2.6 install
# 这里多了一个关键字 PREFIX= 这个关键字的作用是编译的时候用于指定程序存放的路径。
# 比如我们现在就是指定了redis必须存放在/usr/local/redis/redis-6.2.6目录。
# 这里指定好目录也方便后续的卸载，后续直接rm -rf /usr/local/redis/redis-6.2.6 即可删除redis。
```
 3. 修改redis.conf配置文件  (使用 /关键字 查找，n字符切换下一个，输入:noh退回到正常模式) 
将daemonize设置为yes(后台运行)，将bind修改为内网IP。
 4. 在redis-6.2.6目录下启动Redis并查看状态
```powershell
# 启动命令
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis.conf
# 查询运行状态
[root@MService-37 redis-6.2.6]# ps -ef | grep redis
root     16645     1  0 12:45 ?        00:00:00 ./bin/redis-server *:6379
root     16666 14477  0 12:45 pts/10   00:00:00 grep --color=auto redis
# 通过端口查询
[root@MService-37 redis-6.2.6]# netstat -aux | grep 6379

# 打开客户端测试
[root@MService-37 redis-6.2.6]# ./bin/redis-cli 
127.0.0.1:6379> set name jxm
OK
127.0.0.1:6379> keys *
1) "name"
127.0.0.1:6379> get name
"jxm"
127.0.0.1:6379> del name
(integer) 1
127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> exit
[root@MService-37 redis-6.2.6]# 

# 关闭redis服务
[root@MService-37 redis-6.2.6]# ./bin/redis-cli 
127.0.0.1:6379> SHUTDOWN
not connected> exit
[root@MService-37 redis-6.2.6]# ps -ef | grep redis
root     17020 14477  0 12:52 pts/10   00:00:00 grep --color=auto redis
[root@MService-37 redis-6.2.6]# 

```
# 三、集群部署

 1. 在  创建集群节点目录
```powershell
# 节点总目录
[root@MService-37 redis-6.2.6]# mkdir redis-cluster
# 进入目录中
[root@MService-37 redis-6.2.6]# cd redis-cluster/
# 创建六个节点目录，分别为6380 6381 ... 6385
[root@MService-37 redis-cluster]# mkdir {6380..6385}
[root@MService-37 redis-cluster]# ls
6380  6381  6382  6383  6384  6385
```
 2. 复制并修改配置文件
```powershell
# 复制redis.conf文件到6380目录下
[root@MService-37 redis-cluster]# cp /usr/local/redis/redis-6.2.6/redis.conf 6380/
[root@MService-37 redis-cluster]# vim 6380/redis.conf										
```
修改信息如下
```powershell
port 6380																		
timeout 300												
# 修改pid进程文件路径和名称								
pidfile /usr/local/redis/redis-6.2.6/redis-cluster/6380/redis_6380.pid		
# 修改日志文件路径和名称							
logfile /usr/local/redis/redis-6.2.6/redis-cluster/6380/redis_6380.log
# 修改数据文件存放地址
dir /usr/local/redis/redis-6.2.6/redis-cluster/6380
# 配置每个节点的配置文件						
cluster-config-file /usr/local/redis/redis-6.2.6/redis-cluster/6380/nodes-6380.conf
# 开启AOF持久化机制
appendonly yes
# always，同步写回，每个子命令执行完，都立即将日志写回磁盘
# everysec，每个命令执行完，只是先把日志写到AOF内存缓冲区，每隔一秒同步到磁盘
# no：只是先把日志写到AOF内存缓冲区，有操作系统去决定何时写入磁盘
appendfsync everysec
# 启用集群
cluster-enabled yes	
# 配置集群节点的超时时间													
cluster-node-timeout 15000	
```
 3. 将6380下的redis.conf同步到其他五个节点目录下
```powershell
# 复制
[root@MService-37 redis-cluster]# cp 6380/redis.conf 6381
[root@MService-37 redis-cluster]# cp 6380/redis.conf 6382
[root@MService-37 redis-cluster]# cp 6380/redis.conf 6383
[root@MService-37 redis-cluster]# cp 6380/redis.conf 6384
[root@MService-37 redis-cluster]# cp 6380/redis.conf 6385
# 修改
[root@MService-37 redis-cluster]# sed -i "s/6380/6381/g" 6381/redis.conf 
[root@MService-37 redis-cluster]# sed -i "s/6380/6382/g" 6382/redis.conf 
[root@MService-37 redis-cluster]# sed -i "s/6380/6383/g" 6383/redis.conf 
[root@MService-37 redis-cluster]# sed -i "s/6380/6384/g" 6384/redis.conf 
[root@MService-37 redis-cluster]# sed -i "s/6380/6385/g" 6385/redis.conf
```
 4. 分别启动六个redis实例
```powershell
[root@MService-37 redis-cluster]# 
[root@MService-37 redis-cluster]# 
[root@MService-37 redis-cluster]# cd /usr/local/redis/redis-6.2.6
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis-cluster/6380/redis.conf 
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis-cluster/6381/redis.conf 
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis-cluster/6382/redis.conf 
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis-cluster/6383/redis.conf 
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis-cluster/6384/redis.conf 
[root@MService-37 redis-6.2.6]# ./bin/redis-server redis-cluster/6385/redis.conf 
[root@MService-37 redis-6.2.6]# 
[root@MService-37 redis-6.2.6]# 
[root@MService-37 redis-6.2.6]# ps -ef | grep redis
root     21692     1  0 14:24 ?        00:00:00 ./bin/redis-server *:6380 [cluster]
root     21704     1  0 14:24 ?        00:00:00 ./bin/redis-server *:6381 [cluster]
root     21716     1  0 14:24 ?        00:00:00 ./bin/redis-server *:6382 [cluster]
root     21727     1  0 14:24 ?        00:00:00 ./bin/redis-server *:6383 [cluster]
root     21736     1  0 14:24 ?        00:00:00 ./bin/redis-server *:6384 [cluster]
root     21746     1  0 14:24 ?        00:00:00 ./bin/redis-server *:6385 [cluster]
root     21767 11505  0 14:24 pts/3    00:00:00 grep --color=auto redis
[root@MService-37 redis-6.2.6]# 
[root@MService-37 redis-6.2.6]# 
```
 5. 创建集群
```powershell
# 执行如下命令 --cluster-replicas 1 （数字1代表主从比例）
[root@MService-37 redis-6.2.6]# ./bin/redis-cli --cluster create 10.105.10.37:6380 10.105.10.37:6381 10.105.10.37:6382 10.105.10.37:6383 10.105.10.37:6384 10.105.10.37:6385 --cluster-replicas 1
```
期间根据提示输入yes，执行完命令显示以下内容则创建成功，可以看到三主M和三从S的对应关系。
	主 ***6380 > 6384*** 从
	主 ***6381 > 6385*** 从
	主 ***6382 > 6383*** 从
==只有主节点有hash槽位==
```powershell
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 10.105.10.37:6384 to 10.105.10.37:6380
Adding replica 10.105.10.37:6385 to 10.105.10.37:6381
Adding replica 10.105.10.37:6383 to 10.105.10.37:6382
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 34661e7b2293288e696ff1af1347d3c8f1957818 10.105.10.37:6380
   slots:[0-5460] (5461 slots) master
M: 76d465402213048fc5aefac84c84ed32dd8c2b85 10.105.10.37:6381
   slots:[5461-10922] (5462 slots) master
M: e05bcc6e036ed3c6aa857aad009a3de2c2983c7b 10.105.10.37:6382
   slots:[10923-16383] (5461 slots) master
S: 7a0a9c15f785fd125eb75d405a42924aebcad59d 10.105.10.37:6383
   replicates e05bcc6e036ed3c6aa857aad009a3de2c2983c7b
S: 9a338d0f856f96bc013b03a67dcc4b90165f7f30 10.105.10.37:6384
   replicates 34661e7b2293288e696ff1af1347d3c8f1957818
S: fc227dfdeb8c8b03512f10a391d07b2102484be3 10.105.10.37:6385
   replicates 76d465402213048fc5aefac84c84ed32dd8c2b85
Can I set the above configuration? (type 'yes' to accept): yes

>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join

>>> Performing Cluster Check (using node 10.105.10.37:6380)
M: 34661e7b2293288e696ff1af1347d3c8f1957818 10.105.10.37:6380
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: 76d465402213048fc5aefac84c84ed32dd8c2b85 10.105.10.37:6381
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 7a0a9c15f785fd125eb75d405a42924aebcad59d 10.105.10.37:6383
   slots: (0 slots) slave
   replicates e05bcc6e036ed3c6aa857aad009a3de2c2983c7b
S: 9a338d0f856f96bc013b03a67dcc4b90165f7f30 10.105.10.37:6384
   slots: (0 slots) slave
   replicates 34661e7b2293288e696ff1af1347d3c8f1957818
M: e05bcc6e036ed3c6aa857aad009a3de2c2983c7b 10.105.10.37:6382
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: fc227dfdeb8c8b03512f10a391d07b2102484be3 10.105.10.37:6385
   slots: (0 slots) slave
   replicates 76d465402213048fc5aefac84c84ed32dd8c2b85
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
[root@MService-37 redis-6.2.6]# 
```
查询集群信息
```powershell
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6380
10.105.10.37:6380> cluster info
10.105.10.37:6380> cluster nodes
```
# 四、测试
一开连接主节点6380测试添加数据报错，错误信息：==(error) MOVED 5798 10.105.10.37:6381==

```powershell
[root@MService-37 redis-6.2.6]# 
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6380
10.105.10.37:6380> keys *
(empty array)
10.105.10.37:6380> set name jxm
(error) MOVED 5798 10.105.10.37:6381
10.105.10.37:6380> exit
[root@MService-37 redis-6.2.6]# 
```
原因是没有用集群模式连接（连接节点命令 没有加 -c 参数），修改后则测试正常

```powershell
# 打开6380写数据时重定向到了6381，而6381的从节点为6385
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6380 -c
10.105.10.37:6380> keys *
(empty array)
10.105.10.37:6380> set name jxm
-> Redirected to slot [5798] located at 10.105.10.37:6381
OK
10.105.10.37:6381> exit
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6385 -c
10.105.10.37:6385> keys *
1) "name"
10.105.10.37:6385> get name
-> Redirected to slot [5798] located at 10.105.10.37:6381
"jxm"
10.105.10.37:6381> exit


[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6385 -c
10.105.10.37:6385> set phone 110
-> Redirected to slot [8939] located at 10.105.10.37:6381
OK
10.105.10.37:6381> exit
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6383 -c
10.105.10.37:6383> keys *
(empty array)
10.105.10.37:6383> set email 123@qq.com
Error: Broken pipe
10.105.10.37:6383> exit
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6382 -c
10.105.10.37:6382> set email 123@qq.com
-> Redirected to slot [10780] located at 10.105.10.37:6381
OK
10.105.10.37:6381> exit
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6383 -c
10.105.10.37:6383> get email
-> Redirected to slot [10780] located at 10.105.10.37:6381
"123@qq.com"
10.105.10.37:6381> exit
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6384 -c
10.105.10.37:6384> keys *
(empty array)
10.105.10.37:6384> exit
[root@MService-37 redis-6.2.6]# 

```

```powershell
[root@MService-37 redis-6.2.6]# ps -ef | grep redis
root      4820     1  0 15:08 ?        00:00:01 ./bin/redis-server *:6384 [cluster]
root      5098     1  0 15:14 ?        00:00:00 ./bin/redis-server *:6380 [cluster]
root      5232 28834  0 15:16 pts/6    00:00:00 grep --color=auto redis
root     21704     1  0 Apr16 ?        00:06:25 ./bin/redis-server *:6381 [cluster]
root     21716     1  0 Apr16 ?        00:06:11 ./bin/redis-server *:6382 [cluster]
root     21727     1  0 Apr16 ?        00:07:01 ./bin/redis-server *:6383 [cluster]
root     21746     1  0 Apr16 ?        00:06:57 ./bin/redis-server *:6385 [cluster]
[root@MService-37 redis-6.2.6]# kill -9 4820
[root@MService-37 redis-6.2.6]# kill -9 5098
[root@MService-37 redis-6.2.6]# ps -ef | grep redis
root      5254 28834  0 15:16 pts/6    00:00:00 grep --color=auto redis
root     21704     1  0 Apr16 ?        00:06:26 ./bin/redis-server *:6381 [cluster]
root     21716     1  0 Apr16 ?        00:06:11 ./bin/redis-server *:6382 [cluster]
root     21727     1  0 Apr16 ?        00:07:01 ./bin/redis-server *:6383 [cluster]
root     21746     1  0 Apr16 ?        00:06:57 ./bin/redis-server *:6385 [cluster]
[root@MService-37 redis-6.2.6]# ./bin/redis-cli -h 10.105.10.37 -p 6381 -c
10.105.10.37:6381> 
10.105.10.37:6381> keys *
1) "1"
2) "abc"
3) "phone"
4) "hhh"
10.105.10.37:6381> get wocao
(error) CLUSTERDOWN The cluster is down
10.105.10.37:6381> get abc
(error) CLUSTERDOWN The cluster is down
10.105.10.37:6381> get 1
(error) CLUSTERDOWN The cluster is down
10.105.10.37:6381> 

```


---

# 总结
由上面测试可以看出，三主三从集群模式下：

 - cluster集群使用hash槽分片而没有使用hash一致性算法，hash槽一共有16383个。
 - 有三对主从，即一个主节点对应一个从节点，对应三个hash槽区间（0-5460，5461-10922，10923-16383）。
 - 不同主从之间数据独立，客户端添加数据会重定向到对应hash槽的节点上。
 - 主节点不可用时，其对应的从节点会升级为该区间的主节点，当原先的主节点恢复后，原先的主节点就变成了从节点。
 - 当某个区间的主从节点都不可用的时候，整个集群都不可用。