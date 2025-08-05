import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import HomePage from './components/HomePage';
import CustomerForm from './components/CustomerForm';
import BusinessOwnerForm from './components/BusinessOwnerForm';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <div className="nav-container">
            <Link to="/" className="nav-logo">
              Berber Salonu
            </Link>
            <div className="nav-menu">
              <Link to="/" className="nav-link">
                Ana Sayfa
              </Link>
              <Link to="/customer-register" className="nav-link">
                Müşteri Kayıt
              </Link>
              <Link to="/business-register" className="nav-link">
                İşletme Kayıt
              </Link>
            </div>
          </div>
        </nav>
        
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/customer-register" element={<CustomerForm />} />
          <Route path="/business-register" element={<BusinessOwnerForm />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
