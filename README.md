# spring-coindesk

這是一個 Java Spring 演練專案。
可以針對各個貨幣，進行檢視、編輯、新增、刪除。貨幣資料來源是 coindesk。

## API 文件說明

### 檢視所有幣別列表

`[GET] http://localhost:8080/coin`

### 檢視指定幣別

`[GET] http://localhost:8080/coin/{id}`

### 新增幣別

`[POST] http://localhost:8080/coin`

### 更新指定幣別

`[PUT] http://localhost:8080/coin/{id}`

### 刪除指定幣別

`[DELETE] http://localhost:8080/coin/{id}`

## 資料表

### Coin 貨幣

| 欄位名稱   | 資料型態 | 說明           |
|:---------- |:-------- |:-------------- |
| id         | UUID     | 唯一識別流水號 |
| code       | String   | 代號           |
| nameEng    | String   | 貨幣英文名稱   |
| nameChi    | String   | 貨幣中文名稱   |
| rate       | String   | 匯率           |
| createTime | String   | 建立時間       |
| updateTime | String   | 更新時間       |
