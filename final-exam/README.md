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
ftp> ls
200 Command okay
125 Opening ASCII mode data connection for file list.
tp_store_exposure_record.csv
01.png
ui.xml
226 Transfer complete.
ftp> 
ftp> put project-run.sh
200 Command okay
150 Opening binary mode project-run.sh
226 File transfer successful.
1536 bytes sent in 0.0028 seconds (536 kbytes/s)
ftp> 
ftp> get ui.xml
200 Command okay
150 Opening binary mode ui.xml
226 File transfer successful.
41611 bytes received in 0.00455 seconds (8.72 Mbytes/s)
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
