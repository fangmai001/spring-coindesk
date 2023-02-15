# spring-coindesk

這是一個 Java Spring 專案。  
可以針對各個貨幣，進行檢視、編輯、新增、刪除。  
貨幣資料來源是 coindesk (https://api.coindesk.com/v1/bpi/currentprice.json)。

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

### 將所有貨幣與 coindesk 同步更新

`[GET] http://localhost:8080/coin/updateToLatest`

## 資料表

### Coin 貨幣

| 欄位名稱   | 資料型態 | 說明           |
|:---------- |:-------- |:-------------- |
| id         | UUID     | 唯一識別流水號 |
| code       | String   | 代號           |
| nameEng    | String   | 貨幣英文名稱   |
| nameChi    | String   | 貨幣中文名稱   |
| rate       | Float    | 匯率           |
| createTime | Instant  | 建立時間       |
| updateTime | Instant  | 更新時間       |

## SQL語法

### 新增 coin

```roomsql
INSERT INTO coin (code, nameEng, nameChi, rate, createTime, updateTime) 
VALUES 
  (
    $1, $2, $3, $4, $5, $6
  );
```

### 檢視 coin

```roomsql
SELECT 
  code, 
  nameEng, 
  nameChi, 
  rate, 
  createTime, 
  updateTime 
FROM 
  coin 
where 
  id = $1
```

### 更新 coin

```roomsql
UPDATE
coin
SET
code = $1,
nameEng = $2,
nameChi = $3,
rate = $4,
createTime = $5,
updateTime = $6
```

### 刪除 coin

```roomsql
DELETE FROM 
  coin 
WHERE 
  id = $1
```
