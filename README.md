
# under developing

### test
ffmpeg -i mmsh://simuledge.shibapon.net/BAN-BAN_Radio -f mpegts - |m3u8-segmenter -i - -d 7 -p tmp2/bbradio -m tmp2/bb.m3u8 -u http://192.168.2.126:8990

## 代码结构与依赖
hls-m3u8-radio

--m3u8-center 
----m3u8-common

--m3u8-node
----m3u8-common

--m3u8-web
----m3u8-common

* m3u8-common  公用model 接口

* m3u8-node  生成ts的worker
ffmpeg 转码 每n秒upload一次文件
将*.ts 发送给m3u8-center 并简单地保证文件完整性

* m3u8-center
接受worker上传的.ts 文件并统一命名，mv到nginx静态文件夹或者update到server，并存入mongo 

* m3u8-web
根据redis和mongodb 拼m3u8 发送给前台

### 一些问题及初步解决方案
* m3u8-node 解决完整性的方法
```
new Thread and check per 2 second sorted by lastmodifyedtime 并将.ts 发送到m3u8-center 
update成功后发通知给center use hessian
```

* m3u8-center
```
受到hessian请求，然后发布，将.ts 发送到nginx目录并改变命名（db id），并将.ts的文件名与时间与id做对应
ts 文件名 (年月日，节目名字crc，dbid) 20131203_xxxx_id 
```

* 如何检查node是否异常？ 
```
将.ts 发送到nginx目录后，update or insert redis数据，
new Thread check 时间是否相差超过20sec。。。 
```