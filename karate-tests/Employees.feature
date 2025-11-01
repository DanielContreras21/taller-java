Feature: Pruebas de Integración CRUD Empleados

Background:
  * url baseUrl
  * def employeePayload = { name: 'John Doe', position: 'Dev', salary: 50000 }
  Given path 'employees'
  And request employeePayload
  When method POST
  Then status 200
  And match response == { id: '#number', name: '#string', position: '#string', salary: #number }
  * def createdId = response.id

Scenario: TC-001 - Crear Empleado Válido (Happy Path)
  Given path 'employees'
  And request { name: 'Alice', position: 'QA', salary: 40000 }
  When method POST
  Then status 200
  And match response == { id: '#number', name: '#string', position: '#string', salary: #number }

Scenario: TC-002 - Leer Empleado Existente (Happy Path)
  Given path 'employees', createdId
  When method GET
  Then status 200
  And match response == { id: '#number', name: '#string', position: '#string', salary: #number }

Scenario: TC-003 - Actualizar Empleado (Happy Path)
  Given path 'employees', createdId
  And request { name: 'Jane Doe', position: 'Manager', salary: 60000 } 
  When method PUT
  Then status 200
  And match response == 'Updated'

Scenario: TC-004 - Eliminar Empleado (Happy Path)
  Given path 'employees', createdId
  When method DELETE
  Then status 200
  And match response == 'Deleted'

Scenario: TC-005 - Leer Inexistente
  Given path 'employees', 999
  When method GET
  Then status 404

Scenario: TC-006 - Actualizar Inexistente
  Given path 'employees', 999
  And request { name: 'Invalid', position: 'Test' }
  When method PUT
  Then status 404

Scenario: TC-007 - Eliminar Inexistente
  Given path 'employees', 999
  When method DELETE
  Then status 404

Scenario: TC-008 - Crear con Name Vacío
  Given path 'employees'
  And request { name: '', position: 'Dev', salary: 40000 }
  When method POST
  Then status 200  # No hay validación en el controlador

Scenario: TC-009 - Crear con Salary Negativo (Edge)
  Given path 'employees'
  And request { name: 'John', position: 'Dev', salary: -1000 }
  When method POST
  Then status 200  # No hay validación en el controlador
  And match response.id == '#number'
