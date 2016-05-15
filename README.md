# ラジコンシェルジュ番組配信サーバ

- NHKの番組表APIが1日300回しか叩けないため、データをキャッシュしてアプリへ配信する中間サーバを作成

# How to run

- ```src/main/resources/config/application.yml``` に下記を追加 (ファイルが無ければ作成)

```yml:src/main/resources/config/application.yml
apikey:
    nhk: <application api key>

```

- 下記コマンドで起動

```
$ ./gradle bootRun
```
