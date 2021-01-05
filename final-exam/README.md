## 打包和安装

### maven本地打包

`mvn clean package`

## 使用方式

### 1. 运行jar包

```shell
sh start.sh
- 执行完成以后
  - 输入端口号
  - 输入ftp路径
```

### 2.客户端操作

```shell
HackerMa:~ coderma$ 
HackerMa:~ coderma$ 
HackerMa:~ coderma$ 
HackerMa:~ coderma$ ftp
ftp> open 127.0.0.1 10241
Connected to 127.0.0.1.
220 Welcome to the Fs-FTP-Server
Name (127.0.0.1:coderma): 
230 USER LOGGED IN SUCCESSFULLY
ftp> 
ftp> 
ftp> ls
200 Command okay
125 Opening ASCII mode data connection for file list.
1015-交易.csv
1020-交易.csv
226 Transfer complete.
ftp> 
ftp> 
ftp> hash
Hash mark printing on (1024 bytes/hash mark).
ftp> 
ftp> 
ftp> binary
200 Type set to BINARY
ftp> 
ftp> 
ftp> put 01.png
200 Command okay
150 Opening binary mode 01.png
#########################################################
#########################################################
#########################################################
#########################################################
#########################################################
#########################################################
#########################################################
#######################
226 File transfer successful.
432084 bytes sent in 0.00484 seconds (85.2 Mbytes/s)
ftp> 
ftp> 
ftp> get 1020-交易.csv
200 Command okay
150 Opening binary mode 1020-??.csv
#########################################################
#########################################################
#########################################################
#########################################################
#########################################################
#########################################################
##############################################
226 File transfer successful.
396815 bytes received in 0.00261 seconds (145 Mbytes/s)
ftp> 
ftp> 
ftp> 
ftp> ls
200 Command okay
125 Opening ASCII mode data connection for file list.
01.png
1015-交易.csv
1020-交易.csv
#
226 Transfer complete.
ftp> 
ftp> 
ftp> 
ftp>
```

###     

## Class描述

- FtpNettyServer.java（Main方法)
- core/
  - util/ 工具类
  - command/ FTP命令解析\处理
  - FtpCommandHandler.java 接收客户端消息
