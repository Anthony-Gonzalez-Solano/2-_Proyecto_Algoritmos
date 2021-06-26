/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.list;

public class CircularLinkedList implements List {
    private Node first; //apunta al inicio de la lista dinamica
    private Node last; //apunta al ultimo nodo de la lista
    
    //Constructor
    public CircularLinkedList(){
        this.first = null; this.last = null; //la lista todavia no existe
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        int count = 0;
        while(aux!=last){
            count++;
            aux = aux.next;
        }
        return count+1;
    }

    @Override
    public void clear() {
        this.first = this.last = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        while(aux!=last){
            if(util.Utility.equals(aux.data, element)){
                return true;
            }
            aux = aux.next;
        }
        //verifica el ultimo nodo, por que el while no lo ve
        return util.Utility.equals(last.data, element);
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            this.first = newNode;
            this.last = newNode;
        }else{
         last.next = newNode;
         last = newNode;
         
         last.next = first;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            this.first = this.last = newNode;
        }
        newNode.next = first;
        first = newNode;
        last.next = newNode; //enlace circular
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        //CASO 1. LA LISTA ESTA VACIA
        if(isEmpty()){
            first = last = newNode;
        }else{
            //CASO 2. first.next es nulo, o no es nulo
            //y el elemento a insertar es menor al del inicio
            if(util.Utility.greaterT(first.data, element)){
                newNode.next = first;
                first = newNode;
            }else{
                //CASE 3. TODO LO DEMAS
                Node prev = first;
                Node aux = first.next;
                boolean added=false;
                while(aux!=last&&!added){
                    if(util.Utility.lessT(element, aux.data)){
                        prev.next = newNode;
                        newNode.next = aux;
                        added = true;
                    }
                    prev = aux;
                    aux = aux.next;
                }
                // si llega aqui se enlaza cuando aux = last
                if(util.Utility.lessT(element, aux.data)&&!added){
                    prev.next = newNode;
                    newNode.next = aux;
                }else if(!added){
                    aux.next = newNode;
                    //muevo last al ultimo nodo
                    last = newNode;
                }
            }
        }
        //hago el enlace circular
        last.next = first;
    }

    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
   
        //CASO 1. EL ELEMENTO A SUPRIMIR ES EL PRIMERO DE LA LISTA
        if(util.Utility.equals(first.data, element)){
            first = first.next;
        }else{
        //CASO 2. EL ELEMENTO A SUPRIMIR ESTA EN CUALQUIER OTRO NODO
            Node prev = first; //para dejar rastro, apunta al anterior de aux
            Node aux = first.next;
            while(aux!=last&&!util.Utility.equals(aux.data, element)){
                prev = aux; //un nodo atras de aux
                aux = aux.next;
            }
            //se sale del while cuando alcanza last
            //o cuando encuentra el elemento a suprimir
            
            
            if(util.Utility.equals(aux.data, element)){
                //desenlazo o desconecto el nodo
                prev.next = aux.next;
            }
            //debo asegurarme que last apunte al ultimo nodo
            if(aux == last&&util.Utility.equals(aux.data, element)){
                last = prev;
            }
        }
        last.next = first;
        
        if(first ==last && util.Utility.equals(first.data, element))
            clear();    
    }

    @Override
    public Object removeFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Object element = first.data;
        first = first.next; //muevo el apuntador al sgte nodo
        last.next = first;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        Node prev = first; //para dejar rastro, apunta al anterior de aux
        while(aux.next!=last){
            prev = aux; //un nodo atras de aux
            aux = aux.next;
        }
        //se sale del while cuando esta en el ultimo nodo
        Object element = aux.data;
        last = prev;
        last.next = first;
        return element;
    }

    @Override
    public void sort() throws ListException {
         if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = null;
        for (int i = 1; i < size() ; i++) {
            for (int j = 1; j < size() ; j++) {        
                if (util.Utility.greaterT(getNode(j).getData(), getNode(j+1).getData())) {
                    aux = getNode(j);
                    if(j == 1){
                        first = getNode(j+1);
                        aux.next = first.next;
                        first.next = aux;
                        last.next = first;
                        
                    }else {
                        getNode(j-1).next = getNode(j+1);
                        aux.next = getNode(j).next;
                        getNode(j).next = aux;
                    }
                }
            }
        }
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        int index = 1; //el primer nodo estara en el indice 1
        while(aux!=last){
            if(util.Utility.equals(aux.data, element)){
                return index; //ya lo encontro
            }
            index++;
            aux = aux.next; 
        }
        if(util.Utility.equals(aux.data, element)){
            return index;
        }
        return -1; //significa q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
         if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        Node prev = null;
        while (aux!=last) {
            if(util.Utility.equals(aux.getData(), element)){
                return prev.data;
            }else{
                prev = aux;
                aux=aux.next;
            }   
        }
        if(util.Utility.equals(aux.getData(), element)){
            return prev.data;
        }
       return -1;
    }

    @Override
    public Object getNext(Object element) throws ListException {
         if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        while (aux!=last) {
            if(util.Utility.equals(aux.data, element)){
                return aux.next.data;
            }else{
                aux=aux.next;
            }   
        }
        if(util.Utility.equals(aux.data, element)){
            return aux.next.data;
        }
       return -1;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("CircularLinkedList is empty");
        }
        Node aux = first;
        int i = 1; //el indice del primer elemento de la lista
        while(aux!=last){
            if(util.Utility.equals(i, index)){
                return aux; //ya lo encontro
            }
            i++;
            aux = aux.next; 
        }
        if(util.Utility.equals(i, index)){
            return aux;
        }
        
        return null; //si llega aqui, no encontro el nodo
    }
    
    public void removeDuplicates() throws ListException{
        if(isEmpty()){
            throw new ListException("SinglyLinkedList is empty");
        }
        Node prin = first;
        while(prin!=last){
            Node prev = prin;
            Node aux = prin.next;
            while(aux!=last){
                if(util.Utility.equals(aux.data, prin.data)){
                    prev.next = aux.next;
                    aux=aux.next;
                }else{
                    prev = aux;
                    aux = aux.next;
                }
            }
            if(aux==last){
                if(util.Utility.equals(aux.data, prin.data)){
                    prev.next = aux.next;
                    last=prev;
                }
            }
            prin=prin.next;
        }
    }

    @Override
    public String toString() {
        String result="CIRCULAR LINKED LIST\n";
        Node aux = first;
         //el aux es para moverme por la lista hasta el ult elemento
        while(aux!=last){
            result+=aux.data+"\n";
            aux = aux.next;
        }
        result+=aux.data+"\n";
        return result;
    }
    
    
    
}
