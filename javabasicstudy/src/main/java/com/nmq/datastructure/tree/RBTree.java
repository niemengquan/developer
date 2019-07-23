package com.nmq.datastructure.tree;

/**
 * Created by niemengquan on 2018/1/4.
 */
public class RBTree<T extends Comparable<T>> {
    private RBNode<T>  root;//根节点
    private static final boolean RED=false;//定义红黑树标志
    private static final boolean BLACK=true;
    //定义一个内部的节点类来表示节点
    public class RBNode<T extends Comparable<T>>{
        boolean color;//节点的颜色
        T key;//键值
        RBNode<T> left;//左子节点
        RBNode<T> right;//右子节点
        RBNode<T> parent;//父节点

        public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
        public T getKey(){
            return key;
        }
        public String toString(){
            return ""+key+(this.color==RED?"R":"B");
        }
    }

    //空的构造器
    public RBTree(){
        root=null;
    }

    /**
     *<h1>对红黑树节点x进行左旋操作</h1>
     * 左旋示意图：对接点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     *   左旋做了三件事：
     *  1.将y的左子节点赋值给x的右子节点，并将x赋值给y左子节点的父节点（y左子节点非空时）
     *  2.将x的父节点p（非空时）赋值给y的父节点，同时更新p的子节点为y（左或者右，上图中展示的左子节点）
     *  3.将y的左子节点设为x，将x的父节点设为y
     * @param x
     */
    private void leftRotate(RBNode<T> x){
        if(x.parent==null){
            //x是根节点无法进行左旋操作
            return;
        }
        //1.将y的左子节点赋值给x的右子节点，并将x赋值给y左子节点的父节点（y左子节点非空时）
        RBNode<T> y=x.right;
        x.right=y.left;
        if(y.left!=null)
             y.left.parent=x;
        //2.将x的父节点p（非空时）赋值给y的父节点，同时更新p的子节点为y（左或者右，上图中展示的左子几点）
        RBNode<T> p = x.parent;
        if(p !=null){
            y.parent= p;
            if(p.left==x){//如果x是左子节点
                //则将y设置为左子节点
                p.left=y;
            }else{
                p.right=y;
            }
        }else{
            //父节点为空说明是根节点
            root=y;
            y.parent=null;
        }
        //3.将y的左子节点设为x，将x的父节点设为y
        y.left=x;
        x.parent=y;
    }

    /**
     *<h1>对红黑树节点x进行右旋操作（其实就是左旋的逆操作）</h1>
     * 左旋示意图：对接点x进行右旋
     * <p>        p                     p         </p>
     * <p>       /                     /          </p>
     * <p>      y                     x           </p>
     * <p>     / \                   / \          </p>
     * <p>    x  ry       ----->    lx  y         </p>
     * <p>   / \                       / \        </p>
     * <p>  lx ly                     ly ry       </p>
     *  右旋做了三件事：
     *  <ul>
     *  <li>1.将x的右子节点赋值给y的左子节点，并将y赋值给x右子节点的父节点（x右子节点非空时）</li>
     *  <li>2.将y的父节点p（非空时）赋值给x的父节点，同时更新p的子节点为y（左或者右，上图中展示的左子节点）</li>
     *  <li>3.将x的右子节点设为y，将y的父节点设为x</li>
     *  </ul>
     * @param x
     */
    private void rightRotate(RBNode<T> x){
        RBNode<T> y = x.parent;
        if(y ==null){
            //x是根节点无法进行右旋操作
            return;
        }
        //1.将x的右子节点赋值给y的左子节点，并将y赋值给x右子节点的父节点（x右子节点非空时）
        y.left=x.right;
        if(x.right!=null){
            x.right.parent=y;
        }
        // 2.将y的父节点p（非空时）赋值给x的父节点，同时更新p的子节点为y（左或者右，上图中展示的左子节点）
        RBNode<T> p = y.parent;
        if(p==null){
            //设置x为根节点
            root=x;
            x.parent=null;
        }else{
            x.parent=p;
            if(p.left==y){
                //y是左节点，则将x设置为左节点
                p.left=x;
            }else{
                //y是右节点，则将x设置为右节点
                p.right=x;
            }
        }
        //3.将x的右子节点设为y，将y的父节点设为x
        x.right=y;
        y.parent=x;
    }

    /**
     * 向红黑树中插入节点:
     *   在红-黑树中插入的节点都是红色的，这不是偶然的，因为插入一个红色节点比插入一个黑色节点违背红-黑规则的可能性更小。
     *    原因是：插入黑色节点总会改变黑色高度（违背规则4），但是插入红色节点只有一半的机会会违背规则3。另外违背规则3比违背规则4要更容易修正。
     * @param key
     */
    public void insert(T key){
        RBNode<T> node=new RBNode<T>(RED,key,null,null,null);
        if(node!=null){
            insert(node);
        }
    }

    /**
     * 将节点插入到红黑树中
     * @param node
     */
    private void insert(RBNode<T> node) {
        RBNode<T> current=null;//表示最后node插入的父节点对象
        RBNode<T> x = this.root;//使用root节点开始向下搜索
        //1.找到插入位置
        while (x!=null){
            current=x;
            int compareTo = node.key.compareTo(x.key);
            if(compareTo>0){
                x=x.right;
            }else if(compareTo==0){
                //不做变化
                return;
            }else{
                x=x.left;
            }
        }
        node.parent=current;//找到了位置，将当前current作为node的父节点
        if(current==null){
            //首次插入
            this.root=node;
        }else {
            //判断出入左节点还是右节点
            int compareTo = node.key.compareTo(current.key);
            if(compareTo>0){
                current.right=node;
            }else{
                current.left=node;
            }
        }
        
        //插入元素可能破坏了红黑树的性质，这里要进行修复
        insertFixUp(node);
    }

    /**
     * <h3>插入元素之后进行树的修复</h3>
     *   如果是第一次插入，由于原树为空，所以只会违反红-黑树的规则2，所以只要把根节点涂黑即可；
     *   如果插入节点的父节点是黑色的，那不会违背红-黑树的规则，什么也不需要做；
     *   但是遇到如下三种情况时，我们就要开始变色和旋转了：
     *         1. 插入节点的父节点和其叔叔节点（祖父节点的另一个子节点）均为红色的；
     *         2. 插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的右子节点；
     *         3. 插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的左子节点。
     *
     * @param node
     */
    private void insertFixUp(RBNode<T> node) {
        RBNode<T> parent,gparent;//定义父节点和祖父节点

        //需要修整的条件：父节点存在，且父节点的颜色是红色
       while((parent=getParent(node))!=null&&isRed(parent)) {
           gparent=getParent(parent);

           if(parent==gparent.left){
               //如果父节点是祖父节点的左子节点
               RBNode<T> uncle = gparent.right;//获取叔叔节点
               //case one:叔叔节点也是红色
               if(uncle!=null&&isRed(uncle)){
                   //把父节点和叔叔节点涂黑
                   setBlack(parent);
                   setBlack(uncle);
                   //把祖父节点涂红
                   setRed(gparent);
                   //把位置放到祖父节点处
                   node=gparent;
                   continue;//继续while循环，重新判断
               }
               //case two:叔叔节点是黑色，且当前节点是右子节点
               if(node==parent.right){
                   //从父节点处左旋
                   leftRotate(parent);
                   RBNode<T> temp=parent;//然后将父节点和自己调换一下，为下面右旋做准备
                   parent=node;
                   node=temp;
               }
               //case three:叔叔节点是黑色，且当前节点是左子节点
               setBlack(parent);
               setRed(gparent);
               rightRotate(gparent);
           }else{
               //若父节点是祖父节点的右子节点,与上面的完全相反，本质一样的
               RBNode<T> uncle = gparent.left;//获取叔叔节点
               //case one:叔叔节点也是红色
               if(uncle!=null&&isRed(uncle)){
                   //把父节点和叔叔节点涂黑
                   setBlack(parent);
                   setBlack(uncle);
                   //把祖父节点涂红
                   setRed(gparent);
                   //把位置放到祖父节点处
                   node=gparent;
                   continue;//继续while循环，重新判断
               }
               //case two:叔叔节点是黑色，且当前节点是左子节点
               if(node==parent.left){
                   //从父节点处右旋
                   rightRotate(parent);
                   RBNode<T> temp=parent;//然后将父节点和自己调换一下，为下面右旋做准备
                   parent=node;
                   node=temp;
               }
               //case three:叔叔节点是黑色，且当前节点是左子节点
               setBlack(parent);
               setRed(gparent);
               leftRotate(gparent);
           }

       }
       //将根节点设置为黑色
        setBlack(this.root);

    }

    /**
     * 判断节点是否是红色节点
     * @param node
     * @return
     */
    public boolean isRed(RBNode<T> node){
        return (node!=null)&&(node.color==RED)?true:false;

    }

    /**
     * 判断节点是否是黑色节点
     * @param node
     * @return
     */
    public boolean isBlack(RBNode<T> node){
        return (node!=null)&&(node.color==BLACK)?true:false;
    }

    /**
     * 设置节点的颜色为红色
     * @param node
     */
    public  void setRed(RBNode<T> node){
        if(node!=null)
            node.color=RED;
    }
    /**
     * 设置节点的颜色为黑色
     * @param node
     */
    public  void setBlack(RBNode<T> node){
        if(node!=null)
            node.color=BLACK;
    }

    /**
     * 获取节点的颜色
     * @param node
     * @return 如果节点为空返回黑色
     */
    public boolean getColor(RBNode<T> node){
        return node!=null?node.color:BLACK;
    }

    /**
     * 获取节点的父节点
     * @param node
     * @return
     */
    public RBNode<T> getParent(RBNode<T> node) {
        return node!=null?node.parent:null;
    }

    /**
     * 设置父节点
     * @param node
     * @param parent
     */
    public void setParent(RBNode<T> node,RBNode<T> parent){
        if(node!=null)
            node.parent=parent;
    }
    public void setColor(RBNode<T> node,boolean color){
        if(node!=null){
            node.color=color;
        }
    }
}
