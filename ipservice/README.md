# about ip service

实现一个IP白名单过滤算法，实现以下接口
+ `boolean addWhiteIpAddress(String ip);`
+ `boolean isWhiteIpAddress(String ip);`

要求如下：
占用空间尽量少
运算效率尽量高
在内存中完成查询及判断
接口可能被并发询问
尽量能存储整个IP地址空间
代码可运行，且包含单测

# start server

```shell
> mvn clean package -DskipTests
> java -jar ./target/ip-service-0.0.1.jar
```

# test

### python
`generate_ips`随机生成ip address保存到`ips.csv`文件，`add_white_ip`从`ips.csv`文件读取ip address列表发送请求添加白名单，
`check_ip`从`ips.csv`文件读取ip address列表发送请求校验白名单。
```shell
> cd ${PROJECT-PATH}/src/test/python
> python white_ip_address.py
```

### java spring boot test

+ `IpServiceApplicationTests.testIsWhiteIpAddress` : 单独测试校验ip白名单接口
+ `IpServiceApplicationTests.testAddWhiteIpAddress`: 单独测试添加ip白名单接口
+ `IpServiceApplicationTests.testMock`: mock http request 并发测试添加和校验两个接口
