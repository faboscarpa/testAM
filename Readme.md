# Almundo - Examen Callcenter

##Problema
Existe un call center donde hay 3 tipos de empleados: operador,
supervisor y director. El proceso de la atención de una llamada
telefónica en primera instancia debe ser atendida por un operador, si
no hay ninguno libre debe ser atendida por un supervisor, y de no
haber tampoco supervisores libres debe ser atendida por un director.

## Solución

Se plantea una queue de etrada para las llamadas (ActiveMQ) desde donde las lee el Dispatcher(consumer multiThread), este llama a EmployeeService y le asigna un empleado priorizado que retira de una una PriorityBlockingQueue, se simula la ejecucion de la llamada con un thread.sleep() y se redispoibiliza al empledao agregandolo nuevamente a la PriorityQueue. 

# Extras:

  - **Dar alguna solución sobre qué pasa con una llamada cuando no
hay ningún empleado libre:** Para solucionar este problema utilice una PriorityBloclingQueue, se queda a la espera de que se libere algun operador. Si se cumple un tiempo cobfigurable sin ser atendida la llamada la misma es cortada. 
  - **Dar alguna solución sobre qué pasa con una llamada cuando
entran más de 10 llamadas concurrentes:** Quedaran encoladas en ActiveMQ hasta que el Dispatcher pueda tomarlas ya que el consumer esta configurado con un maximo de 10 concurrencias.  
