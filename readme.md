# Proxy App zu Testzwecken

Proxy-Servlet um REST-Requests an die entsprechenden Backends weiterzuleiten.
 
Requests können künstlich verlangsamt bzw. fehlerhaft beantwortet werden. Durch env-Variable wait (msec Warten) bzw. error (Statuscode der Response).

Das ganze basiert auf dem Jetty Transparent Proxy Servlet.

# Konfiguration per System Environment / Property

- proxyTo = URL zum Zielsystem, z.b https://mein-rest-service.intra.de
- wait = Verzögerung des Requests in msec (Default: 0), Bsp: 10000
- error = Statuscode des Requests (Default: Statuscode des Zielservices, keine Änderung) , Bsp: 500
 
z.B:
java -Dwait=10000 -DproxyTo=http://meinService -jar target/proxy-app.jar


# Nutzung

Das Zielsystem kann nun unter der Proxy-URL genutzt werden z.B: https://meinproxy-i.app-test.de/normale/weitere/url/v1.