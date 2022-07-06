This program is TCP syslog  server for BAS-IP system. TCP server handler reacts on special events , such as "Door opened" and "Door ${id} opened by host".
in config folder there are 2 config files :

###
application.properties :
```properties
basip.port=80                        - basip panel port to connect (by default is 80)
basip.username=admin				 - basip username to connect (by default is 'admin')
basip.password=123456				 - basip password to connect (by default is '123456')
############################
server.port=30001					 - TCP syslog server port (by default is 30001)

```
###
ipconfig.ini

```ini 
[127.0.0.1]							 - Source IP
ip1=127.0.0.1 						 - IP List to send requests
ip2=127.0.0.1
ip3=127.0.0.1
ip4=127.0.0.1

[127.0.0.2]
ip1=127.0.0.1
ip2=127.0.0.1
ip3=127.0.0.1
ip4=127.0.0.1
```



Location : /app

Files description :

nms.jar - executable jar.

startup.sh - starts server. If server is already running on the same port , then will throw error.

shutdown.sh - kills the server's process if nms.pid is present.

restart.sh - restarts the server.

nms.pid - server process ID




PI credentials :

```
username : pi
password : raspberry
```

PI configuration :
```
Java version 8+

CRONTAB :
@reboot cd /app && ./restart.sh
0 0 */3 * * cd /app && ./restart.sh
```



Git repository :
https://github.com/Gaena/nms
