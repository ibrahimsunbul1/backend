import React, { useState } from 'react';
import './BusinessOwnerForm.css';

const BusinessOwnerForm = () => {
  const [formData, setFormData] = useState({
    businessName: '',
    ownerName: '',
    ownerSurname: '',
    phone: '',
    email: '',
    address: '',
    city: '',
    district: '',
    postalCode: '',
    taxNumber: '',
    services: [],
    workingHours: {
      monday: { open: '09:00', close: '18:00', closed: false },
      tuesday: { open: '09:00', close: '18:00', closed: false },
      wednesday: { open: '09:00', close: '18:00', closed: false },
      thursday: { open: '09:00', close: '18:00', closed: false },
      friday: { open: '09:00', close: '18:00', closed: false },
      saturday: { open: '09:00', close: '18:00', closed: false },
      sunday: { open: '09:00', close: '18:00', closed: true }
    },
    description: ''
  });

  const availableServices = [
    { name: 'Saç Kesimi', price: 50 },
    { name: 'Sakal Tıraşı', price: 30 },
    { name: 'Saç + Sakal', price: 70 },
    { name: 'Saç Yıkama', price: 20 },
    { name: 'Styling', price: 40 },
    { name: 'Kaş Alma', price: 15 },
    { name: 'Maske', price: 25 }
  ];

  const days = {
    monday: 'Pazartesi',
    tuesday: 'Salı',
    wednesday: 'Çarşamba',
    thursday: 'Perşembe',
    friday: 'Cuma',
    saturday: 'Cumartesi',
    sunday: 'Pazar'
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleServiceChange = (service) => {
    setFormData(prev => ({
      ...prev,
      services: prev.services.find(s => s.name === service.name)
        ? prev.services.filter(s => s.name !== service.name)
        : [...prev.services, service]
    }));
  };

  const handleWorkingHoursChange = (day, field, value) => {
    setFormData(prev => ({
      ...prev,
      workingHours: {
        ...prev.workingHours,
        [day]: {
          ...prev.workingHours[day],
          [field]: value
        }
      }
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('İşletme Bilgileri:', formData);
    alert('İşletme kaydı başarıyla oluşturuldu!');
    // Burada API çağrısı yapılabilir
  };

  return (
    <div className="business-form-container">
      <div className="business-form">
        <h1>İşletme Sahibi Kayıt Formu</h1>
        <p className="form-description">
          Berber salonunuzu kaydetmek için lütfen aşağıdaki bilgileri doldurun.
        </p>
        
        <form onSubmit={handleSubmit}>
          {/* İşletme Bilgileri */}
          <div className="form-section">
            <h2>İşletme Bilgileri</h2>
            
            <div className="form-group">
              <label htmlFor="businessName">İşletme Adı *</label>
              <input
                type="text"
                id="businessName"
                name="businessName"
                value={formData.businessName}
                onChange={handleInputChange}
                required
                placeholder="Berber Salonu Adı"
              />
            </div>
            
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="ownerName">Sahip Adı *</label>
                <input
                  type="text"
                  id="ownerName"
                  name="ownerName"
                  value={formData.ownerName}
                  onChange={handleInputChange}
                  required
                  placeholder="Adınız"
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="ownerSurname">Sahip Soyadı *</label>
                <input
                  type="text"
                  id="ownerSurname"
                  name="ownerSurname"
                  value={formData.ownerSurname}
                  onChange={handleInputChange}
                  required
                  placeholder="Soyadınız"
                />
              </div>
            </div>
            
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="phone">Telefon *</label>
                <input
                  type="tel"
                  id="phone"
                  name="phone"
                  value={formData.phone}
                  onChange={handleInputChange}
                  required
                  placeholder="(0555) 123 45 67"
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="email">E-posta *</label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  required
                  placeholder="ornek@email.com"
                />
              </div>
            </div>
            
            <div className="form-group">
              <label htmlFor="taxNumber">Vergi Numarası</label>
              <input
                type="text"
                id="taxNumber"
                name="taxNumber"
                value={formData.taxNumber}
                onChange={handleInputChange}
                placeholder="1234567890"
              />
            </div>
          </div>
          
          {/* Adres Bilgileri */}
          <div className="form-section">
            <h2>Adres Bilgileri</h2>
            
            <div className="form-group">
              <label htmlFor="address">Adres *</label>
              <textarea
                id="address"
                name="address"
                value={formData.address}
                onChange={handleInputChange}
                required
                placeholder="Tam adres bilgisi"
                rows="3"
              ></textarea>
            </div>
            
            <div className="form-row">
              <div className="form-group">
                <label htmlFor="city">Şehir *</label>
                <input
                  type="text"
                  id="city"
                  name="city"
                  value={formData.city}
                  onChange={handleInputChange}
                  required
                  placeholder="İstanbul"
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="district">İlçe *</label>
                <input
                  type="text"
                  id="district"
                  name="district"
                  value={formData.district}
                  onChange={handleInputChange}
                  required
                  placeholder="Kadıköy"
                />
              </div>
              
              <div className="form-group">
                <label htmlFor="postalCode">Posta Kodu</label>
                <input
                  type="text"
                  id="postalCode"
                  name="postalCode"
                  value={formData.postalCode}
                  onChange={handleInputChange}
                  placeholder="34000"
                />
              </div>
            </div>
          </div>
          
          {/* Hizmetler */}
          <div className="form-section">
            <h2>Sunulan Hizmetler</h2>
            <div className="services-grid">
              {availableServices.map(service => (
                <label key={service.name} className="service-checkbox">
                  <input
                    type="checkbox"
                    checked={formData.services.find(s => s.name === service.name) ? true : false}
                    onChange={() => handleServiceChange(service)}
                  />
                  <span className="checkmark"></span>
                  <div className="service-info">
                    <span className="service-name">{service.name}</span>
                    <span className="service-price">₺{service.price}</span>
                  </div>
                </label>
              ))}
            </div>
          </div>
          
          {/* Çalışma Saatleri */}
          <div className="form-section">
            <h2>Çalışma Saatleri</h2>
            <div className="working-hours">
              {Object.entries(days).map(([dayKey, dayName]) => (
                <div key={dayKey} className="day-schedule">
                  <div className="day-name">{dayName}</div>
                  <div className="day-controls">
                    <label className="closed-checkbox">
                      <input
                        type="checkbox"
                        checked={formData.workingHours[dayKey].closed}
                        onChange={(e) => handleWorkingHoursChange(dayKey, 'closed', e.target.checked)}
                      />
                      Kapalı
                    </label>
                    {!formData.workingHours[dayKey].closed && (
                      <>
                        <input
                          type="time"
                          value={formData.workingHours[dayKey].open}
                          onChange={(e) => handleWorkingHoursChange(dayKey, 'open', e.target.value)}
                        />
                        <span>-</span>
                        <input
                          type="time"
                          value={formData.workingHours[dayKey].close}
                          onChange={(e) => handleWorkingHoursChange(dayKey, 'close', e.target.value)}
                        />
                      </>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>
          
          {/* Açıklama */}
          <div className="form-section">
            <div className="form-group">
              <label htmlFor="description">İşletme Açıklaması</label>
              <textarea
                id="description"
                name="description"
                value={formData.description}
                onChange={handleInputChange}
                placeholder="İşletmeniz hakkında kısa bir açıklama..."
                rows="4"
              ></textarea>
            </div>
          </div>
          
          <button type="submit" className="submit-btn">
            İşletmeyi Kaydet
          </button>
        </form>
      </div>
    </div>
  );
};

export default BusinessOwnerForm;