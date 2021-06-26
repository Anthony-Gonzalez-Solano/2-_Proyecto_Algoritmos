/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.queue;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class LinkedQueue implements Queue {
    private Node front; //apuntador al nodo anterior
    private Node rear; //apuntador al nodo posterior
    private int count; //control de elementos encolados

    public LinkedQueue() {
        front=rear=null;
        count=0;
    }
    

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        front=rear=null;
        count=0;
    }

    @Override
    public boolean isEmpty() {
        return front==null;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is empty");
        }
        LinkedQueue aux = new LinkedQueue();
        int x=0;
        int y=-1; //si es -1 el elemento no existe
        while(!isEmpty()){
            if(util.Utility.equals(front(), element))
                y=x;
            aux.enQueue(deQueue());
            x++;
        }
       //al final  dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return y; //se retorna el indice del elemento, si no esta retorna -1
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);
        if(isEmpty()){ //la cola no existe
            rear = newNode;
            //garantizo que anterior quede apuntando al primer nodo
            front = rear;
        }else{ //que pasa si ya hay elementos encolados
            rear.next = newNode;
            //rear siempre debe apuntar al ultimo elemento encolado
            rear = rear.next; 
        }
        //actualizo el contador
        count++;
    }
    
    @Override
    public void enQueue(Object element, Integer priority) throws QueueException {
        Node newNode = new Node(element, priority);
        if(isEmpty()){ //la cola no existe
            rear = newNode;
            //garantizo que anterior quede apuntando al primer nodo
            front = rear;
        }else{ //que pasa si ya hay elementos encolados
            Node aux = front;
            Node prev = front;
            while(aux!=null&&aux.priority>=priority){
                prev = aux; //dejo un rastro
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o la prioridad del nuevo elemento
            //es mayor
            if(aux==front){
                newNode.next = front;
                front = newNode;
            }else
                if(aux==null){
                    prev.next = newNode;
                    rear = newNode;
                }else{ //en cualquier otro caso
                    prev.next = newNode;
                    newNode.next = aux;
                }
        }
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is empty");
        }
        Object element = front();
        //caso 1. Cuando solo hay un elemento encolado
        if(front==rear){
            clear(); //elimino la cola
        }else{ //caso 2
            front = front.next;
            //actualizo el contador
            count--;
        }
        return element;
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is empty");
        }
        LinkedQueue aux = new LinkedQueue();
        boolean found = false;
        while(!isEmpty()){
            if(util.Utility.equals(front(), element)){
                found = true;
            }
            aux.enQueue(deQueue());
        }
        //al final  dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return found;
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is empty");
        }
         return front.data;
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty()){
            throw new QueueException("Linked Queue is empty");
        }
        return front.data;
    }
    
    @Override
    public String toString() {
        String result = "\nLinked Queue Content\n";
        try{
            LinkedQueue aux = new LinkedQueue();
            while(!isEmpty()){
                result+=front()+"\n";
                aux.enQueue(deQueue());
            }
            //al final  dejamos la cola en su estado original
            while(!aux.isEmpty()){
                enQueue(aux.deQueue());
            }
        }catch(QueueException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
}
