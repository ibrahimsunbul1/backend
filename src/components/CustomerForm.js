import React, { useState } from 'react';
import './CustomerForm.css';

const CustomerForm = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    phone: '',
    email: '',
    birthDate: '',
    preferredServices: [],
    notes: ''
  });

  const services = [
    'Saç Kesimi',
    'Sakal Tıraşı',
    'Saç + Sakal',
    'Saç Yıkama',
    'Styling'
  ];

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
      preferredServices: prev.preferredServices.includes(service)
        ? prev.preferredServices.filter(s => s !== service)
        : [...prev.preferredServices, service]
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Müşteri Bilgileri:', formData);
    alert('Müşteri kaydı başarıyla oluşturuldu!');
    // Burada API çağrısı yapılabilir
  };

  return (
    <div className="customer-form-container">
      <div className="customer-form">
        <h1>Müşteri Kayıt Formu</h1>
        <p className="form-description">
          Berber salonumuza hoş geldiniz! Lütfen bilgilerinizi doldurun.
        </p>
        
        <form onSubmit={handleSubmit}>
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="firstName">Ad *</label>
              <input
                type="text"
                id="firstName"
                name="firstName"
                value={formData.firstName}
                onChange={handleInputChange}
                required
                placeholder="Adınızı girin"
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="lastName">Soyad *</label>
              <input
                type="text"
                id="lastName"
                name="lastName"
                value={formData.lastName}
                onChange={handleInputChange}
                required
                placeholder="Soyadınızı girin"
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
              <label htmlFor="email">E-posta</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                placeholder="ornek@email.com"
              />
            </div>
          </div>
          
          <div className="form-group">
            <label htmlFor="birthDate">Doğum Tarihi</label>
            <input
              type="date"
              id="birthDate"
              name="birthDate"
              value={formData.birthDate}
              onChange={handleInputChange}
            />
          </div>
          
          <div className="form-group">
            <label>Tercih Edilen Hizmetler</label>
            <div className="services-grid">
              {services.map(service => (
                <label key={service} className="service-checkbox">
                  <input
                    type="checkbox"
                    checked={formData.preferredServices.includes(service)}
                    onChange={() => handleServiceChange(service)}
                  />
                  <span className="checkmark"></span>
                  {service}
                </label>
              ))}
            </div>
          </div>
          
          <div className="form-group">
            <label htmlFor="notes">Özel Notlar</label>
            <textarea
              id="notes"
              name="notes"
              value={formData.notes}
              onChange={handleInputChange}
              placeholder="Özel istekleriniz veya notlarınız..."
              rows="4"
            ></textarea>
          </div>
          
          <button type="submit" className="submit-btn">
            Kayıt Ol
          </button>
        </form>
      </div>
    </div>
  );
};

export default CustomerForm;