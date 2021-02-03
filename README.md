# inspection-s3select
aws s3selectの検証  

参考：https://docs.aws.amazon.com/ja_jp/AmazonS3/latest/dev/Welcome.html  



## cliで試してみる

* アップロードファイル  
  s3://inspection-55/s3select/test001.csv

* cliコマンド
  ```
  aws s3api select-object-content \
      --bucket=inspection-55 --key=s3select/test001.csv \
      --input-serialization '{"CSV":{"QuoteEscapeCharacter":"\\"}}' \
      --output-serialization '{"CSV":{"QuoteFields":"ALWAYS","QuoteEscapeCharacter":"\\"}}' \
      --expression "select * from s3object s " \
      --expression-type SQL output_test001.csv
  ```

* 実行結果
  ```
  root@f62a93c1e38c:/work/s3file# aws s3api select-object-content \
  >     --bucket=inspection-55 --key=s3select/test001.csv \
  >     --input-serialization '{"CSV":{"QuoteEscapeCharacter":"\\"}}' \
  >     --output-serialization '{"CSV":{"QuoteFields":"ALWAYS","QuoteEscapeCharacter":"\\"}}' \
  >     --expression "select * from s3object s " \
  >     --expression-type SQL output_test001.csv
  
  root@f62a93c1e38c:/work/s3file# 
  root@f62a93c1e38c:/work/s3file# cat output_test001.csv 
  "id","pid","flg"
  "id001","pid001","1"
  "id002","pid002","0"
  "id003","pid003","1"
  root@f62a93c1e38c:/work/s3file# cat test001.csv 
  "id","pid","flg"
  "id001","pid001","1"
  "id002","pid002","0"
  "id003","pid003","1"
  root@f62a93c1e38c:/work/s3file# 
  ```




## Javaから動かす
Javaからもとりあえず動かした  
