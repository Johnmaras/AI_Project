package Queue;
//class to represent one node in a list
class ListNode<N>{
	//package access members; List can access these directly
	N data;
	ListNode nextNode;

	//constructor creates a ListNode that refers to object
	ListNode(N object){
		this(object, null);
	}//end ListNode one-argument constructor

	//constructor creates ListNode that refers to
	//Object and to next ListNode
	ListNode(N object, ListNode node){
		data = object;
		nextNode = node;
	}//end ListNode two-argument constructor

	//return reference to data in node
	N getData(){
		return data;//return Object in this node
	}//end method getObject

	//return reference to next node in list
	ListNode getNext(){
		return nextNode;//get next node
	}//end method getNext
}//end class ListNode