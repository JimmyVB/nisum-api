## Run application

Correr la aplicacion

1. Probar desde postman, endpoint: http://localhost:8080/v1/user

Body de ejemplo:
   {
   "name":"Juan Rodriguez",
   "email":"juan@rodriguez.org",
   "password":"Hun33ter2",
   "phones": [
   {
   "number": "1234567",
   "citycode": "1",
   "contrycode": "33"
   }
   ]
   }
3. Se guarda el registro.
4. Para comprobar el insert: http://localhost:8080/h2-console/login.jsp
URL: jdbc:h2:mem:nisumdb
USER: root
PASS: rootroot
5. Dentro de la base de datos h2, seleccionar usuarios y podra visualizar los registros.

6. Para ver el swagger ingresar: http://localhost:8080/swagger-ui/index.html
