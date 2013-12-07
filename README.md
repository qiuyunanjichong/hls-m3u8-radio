
======== under developing

ffmpeg -i mmsh://simuledge.shibapon.net/BAN-BAN_Radio -f mpegts - |m3u8-segmenter -i - -d 7 -p tmp2/bbradio -m tmp2/bb.m3u8 -u http://192.168.2.126:8990

m3u8-node
ffmpeg 转码 每7秒upload一次文件
new Thread check per 2 second sorted by lastmodifyedtime 并将.ts 发送到m3u8-center 
update成功后发通知给center hessian

m3u8-center
受到hessian请求，然后发布，将.ts 发送到nginx目录并改变命名（db id），并将.ts的文件名与时间与id做对应
ts 文件名 (年月日，节目名字crc，dbid) 20131203_xxxx_id 
纪录进db 
延迟：比如说预留20* 7 140秒的延迟

如何检查node是否异常？ 
将.ts 发送到nginx目录后，update or insert redis数据，
new Thread check 时间是否相差超过20sec。。。 

m3u8-web
根据redis 拼m3u8。。
