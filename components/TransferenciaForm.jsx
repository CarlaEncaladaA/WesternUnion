import React, { useState } from 'react';
import '../styles/TransferenciaForm.css';

const TransferenciaForm = () => {
  const [formData, setFormData] = useState({
    remitente: {
      nombre: '',
      email: '',
      telefono: '',
      direccion: '',
    },
    destinatario: {
      nombre: '',
      pais: '',
      numeroCuenta: '',
      codigoPais: '',
      codigoPostal: '',
      descripcionDestino: '',
    },
    monto: '',
    metodoNotificacion: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    const [section, field] = name.split('.');
    setFormData((prevState) => ({
      ...prevState,
      [section]: {
        ...prevState[section],
        [field]: value,
      },
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/transferencia/banco', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          transferencia: {
            remitente: formData.remitente,
            destinatario: formData.destinatario,
            monto: parseFloat(formData.monto),
          },
          metodoNotificacion: formData.metodoNotificacion,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        console.error('Errores:', errorData);
        alert(`Error: ${JSON.stringify(errorData.errores || errorData.error)}`);
      } else {
        const responseData = await response.json();
        console.log('Respuesta del servidor:', responseData);
        alert(`Transferencia realizada con éxito: ID ${responseData.idTransaccion}`);
      }
    } catch (error) {
      console.error('Error en la solicitud:', error);
      alert('Hubo un error al realizar la transferencia.');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Formulario de Transferencia</h1>

      <h2>Datos del Remitente</h2>
      <label htmlFor="remitente.nombre">Nombre*</label>
      <input
        id="remitente.nombre"
        name="remitente.nombre"
        type="text"
        value={formData.remitente.nombre}
        onChange={handleChange}
      />

      <label htmlFor="remitente.email">Email*</label>
      <input
        id="remitente.email"
        name="remitente.email"
        type="email"
        value={formData.remitente.email}
        onChange={handleChange}
      />

      <label htmlFor="remitente.telefono">Teléfono*</label>
      <input
        id="remitente.telefono"
        name="remitente.telefono"
        type="text"
        value={formData.remitente.telefono}
        onChange={handleChange}
      />

      <label htmlFor="remitente.direccion">Dirección*</label>
      <input
        id="remitente.direccion"
        name="remitente.direccion"
        type="text"
        value={formData.remitente.direccion}
        onChange={handleChange}
      />

      <h2>Datos del Destinatario</h2>
      <label htmlFor="destinatario.nombre">Nombre*</label>
      <input
        id="destinatario.nombre"
        name="destinatario.nombre"
        type="text"
        value={formData.destinatario.nombre}
        onChange={handleChange}
      />

      <label htmlFor="destinatario.pais">País*</label>
      <input
        id="destinatario.pais"
        name="destinatario.pais"
        type="text"
        value={formData.destinatario.pais}
        onChange={handleChange}
      />

      <label htmlFor="destinatario.numeroCuenta">Número de Cuenta*</label>
      <input
        id="destinatario.numeroCuenta"
        name="destinatario.numeroCuenta"
        type="text"
        value={formData.destinatario.numeroCuenta}
        onChange={handleChange}
      />

      <label htmlFor="destinatario.codigoPais">Código de País*</label>
      <input
        id="destinatario.codigoPais"
        name="destinatario.codigoPais"
        type="text"
        value={formData.destinatario.codigoPais}
        onChange={handleChange}
      />

      <label htmlFor="monto">Monto*</label>
      <input
        id="monto"
        name="monto"
        type="number"
        value={formData.monto}
        onChange={(e) => setFormData({ ...formData, monto: e.target.value })}
      />

      <label htmlFor="metodoNotificacion">Método de Notificación*</label>
      <select
        id="metodoNotificacion"
        name="metodoNotificacion"
        value={formData.metodoNotificacion}
        onChange={(e) => setFormData({ ...formData, metodoNotificacion: e.target.value })}
      >
        <option value="">Seleccione el método</option>
        <option value="correo electrónico">Correo Electrónico</option>
        <option value="whatsapp">WhatsApp</option>
        <option value="sms">SMS</option>
        <option value="push">Notificación Push</option>
      </select>

      <button type="submit">Realizar Transferencia</button>
    </form>
  );
};

export default TransferenciaForm;
