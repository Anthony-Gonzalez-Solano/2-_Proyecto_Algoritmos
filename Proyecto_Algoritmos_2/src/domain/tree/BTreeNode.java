/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.tree;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class BTreeNode {
    public Object data;
    public BTreeNode left, right;
    public String label; //guarda la ruta donde almaceno el elemento
    
    //AVL BST
    public String sequence; //secuencia de rotaciones
    
    //Constructor No. 1
    public BTreeNode(Object data){
        this.data = data;
        this.left=this.right=null;
    }

    //Constructor No. 2
    public BTreeNode(Object data, String label) {
        this.data = data;
        this.label = label;
        this.sequence = label;
        this.left=this.right=null;
    }
    
}
