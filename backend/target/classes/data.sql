-- ==============================================================================
-- Datos Iniciales de Prueba - InsecureCare Medical Portal
-- NOTA: Contrasenas almacenadas en TEXTO PLANO intencionalmente
-- ==============================================================================

INSERT INTO USUARIOS (username, password, nombre_completo, email, rol) VALUES
('admin', 'admin123', 'Administrador del Sistema', 'admin@insecurecare.ucr.ac.cr', 'ADMIN'),
('dr_vargas', 'doctor2026', 'Dr. Roberto Vargas Castro', 'rvargas@insecurecare.ucr.ac.cr', 'MEDICO'),
('carlos_mora', 'carlos123', 'Carlos Mora Morales', 'cmora@correo.com', 'PACIENTE'),
('maria_rojas', 'maria123', 'María Rojas Soto', 'mrojas@correo.com', 'PACIENTE'),
('ana_solis', 'ana2026', 'Ana Solís Brenes', 'asolis@correo.com', 'PACIENTE');

INSERT INTO EXPEDIENTES_MEDICOS (usuario_id, numero_expediente, diagnostico, tratamiento, observaciones_html, fecha_registro, confidencial) VALUES
(3, 'EXP-2026-001', 'Hipertension Arterial Grado I', 'Enalapril 10mg cada 12 horas y reduccion de sodio.', '<p>Paciente refiere mareos matutinos. <span style="color: blue; font-weight: bold;">Control en 30 dias.</span></p>', '2026-02-15 09:30:00', TRUE),
(4, 'EXP-2026-002', 'Depresion Mayor y Trastorno de Ansiedad', 'Sertralina 50mg diario y psicoterapia cognitivo conductual.', '<p>Tratamiento psiquiatrico confidencial. <span style="color: red;">No divulgar diagnostico a terceros.</span></p>', '2026-03-01 14:15:00', TRUE),
(5, 'EXP-2026-003', 'Diabetes Mellitus Tipo 2 no controlada', 'Metformina 850mg con comidas e Insulina NPH nocturna.', '<p>Glucemia en ayunas &gt; 180 mg/dL. <span style="color: darkorange; font-weight: bold;">Control de glicemia urgente.</span></p>', '2026-03-10 11:00:00', TRUE),
(3, 'EXP-2026-004', 'Laringitis Aguda Infecciosa', 'Amoxicilina 500mg cada 8 horas por 7 dias.', '<p>Reposo de voz absoluto y abundante hidratacion oral.</p>', '2026-03-28 16:45:00', FALSE);

