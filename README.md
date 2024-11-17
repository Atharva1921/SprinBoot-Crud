#Here I willl be uploading my Springboot CRUD operations

# Pagination with filtering and sorting
```
curl --location 'http://localhost:8080/api/employees/filtering&pagination&sorting?size=4&sort=%5B%7B%22field%22%3A%22firstName%22%2C%22direction%22%3A%22desc%22%7D%5D&salary=70000&birth_year=1987&page=0'

```