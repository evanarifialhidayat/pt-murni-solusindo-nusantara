<h1 align="center">API Manajemen Pengguna dengan Otentikasi </h1>
<p align="center"><i>Configuration app run / clean & build.</i></p>
<br>

## Website

Link : http://e-van.cloud:7000

<a href="https://github.com/evanarifialhidayat/ptaz/blob/main/dashboard.png?raw=true"><img src="https://github.com/evanarifialhidayat/ptaz/blob/main/dashboard.png?raw=true"/></a>
<a href="https://github.com/evanarifialhidayat/ptaz/blob/main/listapi.png?raw=true"><img src="https://github.com/evanarifialhidayat/ptaz/blob/main/listapi.png?raw=true"/></a>
<a href="https://github.com/evanarifialhidayat/ptaz/blob/main/loginapi.png?raw=true"><img src="https://github.com/evanarifialhidayat/ptaz/blob/main/loginapi.png?raw=true"/></a>
<a href="https://github.com/evanarifialhidayat/ptaz/blob/main/updateapi.png?raw=true"><img src="https://github.com/evanarifialhidayat/ptaz/blob/main/updateapi.png?raw=true"/></a>
<a href="https://github.com/evanarifialhidayat/ptaz/blob/main/registrasiapi.png?raw=true"><img src="https://github.com/evanarifialhidayat/ptaz/blob/main/registrasiapi.png?raw=true"/></a>
<a href="https://github.com/evanarifialhidayat/ptaz/blob/main/deleteapi.png?raw=true"><img src="https://github.com/evanarifialhidayat/ptaz/blob/main/deleteapi.png?raw=true"/></a>

# API

Link : http://e-van.cloud:7000/api/
- http://e-van.cloud:7000/api/list endpoint untuk registrasi pengguna (nama/email, kata sandi) /GET
- http://e-van.cloud:7000/api/login endpoint untuk menerima data login (email, kata sandi) dan memverifikasi keberadaan pengguna di database /POST
- http://e-van.cloud:7000/api/registrasi endpoint untuk mengambil daftar pengguna /POST
- http://e-van.cloud:7000/api/update/{id} endpoint untuk mengupdate data pengguna /POST
- http://e-van.cloud:7000/api/delete/{id} endpoint untuk menghapus data pengguna /DELETE

# Development

- Java 11
- Maven
- Spring boot
- postgresql 

# Configuration

- Buat rule dengan username 'ptaz' dan password 'ptaz'
- buat database dengan nama 'ptaz'

# Clean & Build

mvn install

# Run

java -jar /target/ptaz-0.0.1-SNAPSHOT.jar

## :pencil: License

This project is licensed under [MIT](https://opensource.org/licenses/MIT) license.

