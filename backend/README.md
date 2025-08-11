# Barbershop Appointment System - Backend

Bu proje, kuaför/berber işletmeleri için randevu yönetim sistemi backend uygulamasıdır. Java 17 ve Spring Boot kullanılarak geliştirilmiştir.

## Özellikler

- **Müşteri Yönetimi**: Müşteri kayıt, güncelleme, listeleme ve arama
- **İşletme Sahibi Yönetimi**: İşletme sahibi kayıt ve yönetimi
- **Randevu Yönetimi**: Randevu oluşturma, güncelleme, iptal etme
- **Bildirim Sistemi**: Gerçek zamanlı bildirimler (WebSocket)
- **PostgreSQL Veritabanı**: Güvenli ve ölçeklenebilir veri saklama

## Teknolojiler

- **Java 17**
- **Spring Boot 2.7.x**
- **Spring Data JPA**
- **Spring WebSocket**
- **PostgreSQL**
- **Maven**
- **Validation API**

## Kurulum

### Gereksinimler

- Java 17 veya üzeri
- PostgreSQL 12 veya üzeri
- Maven 3.6 veya üzeri

### Veritabanı Kurulumu

1. PostgreSQL'de yeni bir veritabanı oluşturun:
```sql
CREATE DATABASE barbershop_db;
```

2. `src/main/resources/application.yml` dosyasındaki veritabanı ayarlarını güncelleyin:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/barbershop_db
    username: your_username
    password: your_password
```

### Uygulamayı Çalıştırma

1. Projeyi klonlayın veya indirin
2. Proje dizinine gidin:
```bash
cd backend
```

3. Maven ile bağımlılıkları yükleyin:
```bash
mvn clean install
```

4. Uygulamayı başlatın:
```bash
mvn spring-boot:run
```

Uygulama `http://localhost:8080/api` adresinde çalışacaktır.

## API Endpoints

### Müşteri İşlemleri
- `POST /api/customers` - Yeni müşteri oluştur
- `GET /api/customers` - Tüm müşterileri listele
- `GET /api/customers/{id}` - Müşteri detayı
- `PUT /api/customers/{id}` - Müşteri güncelle
- `DELETE /api/customers/{id}` - Müşteri sil
- `GET /api/customers/search?query={query}` - Müşteri ara

### İşletme Sahibi İşlemleri
- `POST /api/business-owners` - Yeni işletme sahibi oluştur
- `GET /api/business-owners` - Tüm işletme sahiplerini listele
- `GET /api/business-owners/{id}` - İşletme sahibi detayı
- `PUT /api/business-owners/{id}` - İşletme sahibi güncelle
- `DELETE /api/business-owners/{id}` - İşletme sahibi sil

### Randevu İşlemleri
- `POST /api/appointments` - Yeni randevu oluştur
- `GET /api/appointments` - Tüm randevuları listele
- `GET /api/appointments/{id}` - Randevu detayı
- `PUT /api/appointments/{id}` - Randevu güncelle
- `PATCH /api/appointments/{id}/status` - Randevu durumu güncelle
- `DELETE /api/appointments/{id}` - Randevu sil
- `GET /api/appointments/business/{businessOwnerId}` - İşletme sahibinin randevuları

### Bildirim İşlemleri
- `GET /api/notifications/business/{businessOwnerId}` - İşletme sahibinin bildirimleri
- `GET /api/notifications/business/{businessOwnerId}/unread` - Okunmamış bildirimler
- `PATCH /api/notifications/{id}/read` - Bildirimi okundu olarak işaretle
- `DELETE /api/notifications/{id}` - Bildirim sil

## WebSocket Bağlantısı

Gerçek zamanlı bildirimler için WebSocket bağlantısı:
- **Endpoint**: `ws://localhost:8080/api/ws`
- **Topic**: `/topic/notifications/{businessOwnerId}`

### Frontend'de WebSocket Kullanımı

```javascript
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const socket = new SockJS('http://localhost:8080/api/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    
    // İşletme sahibi ID'sine göre bildirimleri dinle
    stompClient.subscribe('/topic/notifications/' + businessOwnerId, function (notification) {
        const notificationData = JSON.parse(notification.body);
        console.log('Yeni bildirim:', notificationData);
        // Bildirimi UI'da göster
    });
});
```

## Veritabanı Şeması

### Tablolar

1. **customers** - Müşteri bilgileri
2. **business_owners** - İşletme sahibi bilgileri
3. **appointments** - Randevu bilgileri
4. **notifications** - Bildirim bilgileri

### İlişkiler

- Bir müşteri birden fazla randevuya sahip olabilir
- Bir işletme sahibi birden fazla randevuya sahip olabilir
- Bir işletme sahibi birden fazla bildirime sahip olabilir
- Bir randevu bir bildirime sahip olabilir

## Örnek Kullanım

### Yeni Randevu Oluşturma

```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "businessOwnerId": 1,
    "appointmentDate": "2024-01-20T10:00:00",
    "services": "Saç kesimi, Sakal tıraşı",
    "totalPrice": 150.00,
    "notes": "Kısa saç modeli"
  }'
```

Bu işlem sonucunda:
1. Randevu veritabanına kaydedilir
2. İşletme sahibine otomatik bildirim gönderilir
3. WebSocket üzerinden gerçek zamanlı bildirim iletilir

## Geliştirme

### Test Verisi

Uygulama başlatıldığında `data.sql` dosyasındaki örnek veriler otomatik olarak yüklenir:
- 3 işletme sahibi
- 5 müşteri
- 6 randevu
- 9 bildirim

### Loglama

Uygulama logları `logs/` dizininde saklanır:
- `application.log` - Genel uygulama logları
- `error.log` - Hata logları

## Katkıda Bulunma

1. Projeyi fork edin
2. Feature branch oluşturun (`git checkout -b feature/yeni-ozellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/yeni-ozellik`)
5. Pull Request oluşturun

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır.