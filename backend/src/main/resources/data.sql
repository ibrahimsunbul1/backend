-- Sample data for barbershop appointment system
-- This script inserts sample data for testing purposes

-- Insert sample business owners
INSERT INTO business_owners (business_name, owner_name, owner_surname, phone, email, address, city, district, postal_code, tax_number, services, working_hours, description) VALUES
('Elit Kuaför', 'Mehmet', 'Yılmaz', '05551234567', 'mehmet@elitkuafor.com', 'Atatürk Caddesi No:15', 'İstanbul', 'Kadıköy', '34710', '1234567890', 'Saç kesimi, Sakal tıraşı, Yıkama, Fön', 'Pazartesi-Cumartesi: 09:00-19:00, Pazar: Kapalı', 'Profesyonel kuaför hizmetleri'),
('Modern Berber', 'Ali', 'Kaya', '05559876543', 'ali@modernberber.com', 'İstiklal Caddesi No:42', 'İstanbul', 'Beyoğlu', '34430', '9876543210', 'Saç kesimi, Sakal şekillendirme, Cilt bakımı', 'Her gün: 10:00-20:00', 'Modern tarzda berber hizmetleri'),
('Klasik Kuaför', 'Fatma', 'Demir', '05557891234', 'fatma@klasikkuafor.com', 'Cumhuriyet Meydanı No:8', 'Ankara', 'Çankaya', '06100', '1122334455', 'Kadın kuaförü, Makyaj, Gelin başı', 'Salı-Pazar: 09:00-18:00, Pazartesi: Kapalı', 'Kadınlara özel kuaför hizmetleri');

-- Insert sample customers
INSERT INTO customers (first_name, last_name, phone, email, birth_date, preferred_services, notes) VALUES
('Ahmet', 'Özkan', '05551111111', 'ahmet.ozkan@email.com', '1990-05-15', 'Saç kesimi, Sakal tıraşı', 'Kısa saç modeli tercih ediyor'),
('Ayşe', 'Yıldız', '05552222222', 'ayse.yildiz@email.com', '1985-08-22', 'Saç kesimi, Fön', 'Uzun saçlı, katmanlı kesim seviyor'),
('Emre', 'Çelik', '05553333333', 'emre.celik@email.com', '1992-12-03', 'Saç kesimi, Sakal şekillendirme', 'Modern tarzları tercih ediyor'),
('Zeynep', 'Arslan', '05554444444', 'zeynep.arslan@email.com', '1988-03-18', 'Saç kesimi, Makyaj', 'Özel günler için makyaj yaptırıyor'),
('Burak', 'Şahin', '05555555555', 'burak.sahin@email.com', '1995-07-10', 'Saç kesimi, Yıkama', 'Hızlı hizmet istiyor');

-- Insert sample appointments
INSERT INTO appointments (customer_id, business_owner_id, appointment_date, services, total_price, notes, status, notification_sent) VALUES
(1, 1, '2024-01-15 10:00:00', 'Saç kesimi, Sakal tıraşı', 150.00, 'Kısa saç modeli', 'CONFIRMED', true),
(2, 3, '2024-01-15 14:00:00', 'Saç kesimi, Fön', 200.00, 'Katmanlı kesim', 'PENDING', false),
(3, 2, '2024-01-16 11:30:00', 'Saç kesimi, Sakal şekillendirme', 180.00, 'Modern tarz', 'CONFIRMED', true),
(4, 3, '2024-01-16 16:00:00', 'Saç kesimi, Makyaj', 350.00, 'Özel gün makyajı', 'PENDING', false),
(5, 1, '2024-01-17 09:00:00', 'Saç kesimi, Yıkama', 100.00, 'Hızlı hizmet', 'COMPLETED', true),
(1, 2, '2024-01-18 15:00:00', 'Saç kesimi', 120.00, 'Klasik kesim', 'PENDING', false);

-- Insert sample notifications
INSERT INTO notifications (business_owner_id, appointment_id, title, message, type, is_read) VALUES
(1, 1, 'Yeni Randevu', 'Ahmet Özkan adlı müşteriden yeni randevu talebi geldi.', 'APPOINTMENT', false),
(1, 5, 'Randevu Tamamlandı', 'Burak Şahin ile olan randevu tamamlandı.', 'APPOINTMENT', true),
(2, 3, 'Randevu Onaylandı', 'Emre Çelik ile olan randevu onaylandı.', 'APPOINTMENT', false),
(2, 6, 'Yeni Randevu', 'Ahmet Özkan adlı müşteriden yeni randevu talebi geldi.', 'APPOINTMENT', false),
(3, 2, 'Yeni Randevu', 'Ayşe Yıldız adlı müşteriden yeni randevu talebi geldi.', 'APPOINTMENT', false),
(3, 4, 'Yeni Randevu', 'Zeynep Arslan adlı müşteriden yeni randevu talebi geldi.', 'APPOINTMENT', false),
(1, NULL, 'Sistem Bildirimi', 'Hoş geldiniz! Sistem başarıyla kuruldu.', 'SYSTEM', true),
(2, NULL, 'Sistem Bildirimi', 'Hoş geldiniz! Sistem başarıyla kuruldu.', 'SYSTEM', true),
(3, NULL, 'Sistem Bildirimi', 'Hoş geldiniz! Sistem başarıyla kuruldu.', 'SYSTEM', true);