/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.stack;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class LinkedStack implements Stack {
    private Node top;
    private int count; //contador de elementos
    
    //Constructor
    public LinkedStack(){
        this.top = null;
        this.count = 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        top = null;
        count = 0; //reiniciamos el contador
    }

    @Override
    public boolean isEmpty() {
        return top==null; //opcion 1
        //return count==0; //opcion 2
    }

    @Override
    public Object peek() throws StackException {
         if(isEmpty())
            throw new StackException("The linked stack is empty");
         return top.data;
    }

    @Override
    public Object top() throws StackException {
        if(isEmpty())
            throw new StackException("The linked stack is empty");
        return top.data;
    }

    @Override
    public void push(Object element) throws StackException {
        Node newNode = new Node(element);
        if(isEmpty()){
            //creamos un nuevo nodo
            top = newNode;
        }else{ //que pasa si la pila no esta vacia
            newNode.next = top; //hacemos un enlace entre nodos
            top = newNode; //le decimos a top q apunte al nuevo nodo
            
        }
        count++; //incremento el contador de elementos
    }

    @Override
    public Object pop() throws StackException {
        if(isEmpty())
            throw new StackException("The linked stack is empty");
        Object element = top.data;
        top = top.next; //movemos top al sgte nodo
        count--; //decrementamos el contador
        return element;
    }
    
    @Override
    public String toString(){
        String result="LINKED STACK\n";
        try{
            LinkedStack auxStack = new LinkedStack();
            while(!isEmpty()){
                result+=peek()+" ";
                auxStack.push(pop());
            }
            //se sale del while cuando la pila esta vacia
            //en este momento, debemos dejar la pila es su estado original
            while(!auxStack.isEmpty()){
                push(auxStack.pop());
            }
        }catch(StackException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
}
