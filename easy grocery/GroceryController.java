import java.util.ArrayList;
public class GroceryController implements GroceryScreen,GoTo
{
   
    private static GroceryController gc = null;
    private GroceryScreen current;
    /*screen */
    private Category category;
    private Dairy dairy;
    private Vegetables veg; 
    private Bakery bakery;
    private Wine wine; 
    private MyCart mycart;
    private CheckOut checkout;
    
    /*Commands*/
    private ScreenCommand gocategory;
    private ScreenCommand godairy;
    private ScreenCommand goveg;
    private ScreenCommand gobakery;
    private ScreenCommand gowine;
    private ScreenCommand gocart;
    private ScreenCommand gocheckout;
    
    /*History */
     private ArrayList<GroceryScreen> history;
     
     /*item observers for mycart */
     private ArrayList<ItemObserver> observers ;
    
   
    public void initiate()
    {
        /*screen */
        category = new Category();
        dairy = new Dairy();
        veg = new Vegetables();
        bakery = new Bakery();
        wine = new Wine();
        mycart = new MyCart();
        checkout = new CheckOut();
        
        /*Commands*/
        
        gocategory = new ScreenCommand(category);
        godairy = new ScreenCommand(dairy);
        goveg = new ScreenCommand(veg);
        gobakery = new ScreenCommand(bakery);
        gowine = new ScreenCommand(wine);
        gocart = new ScreenCommand(mycart);
        gocheckout = new ScreenCommand(checkout);
        
        /*history */
        history = new ArrayList<GroceryScreen>();
        
        /* ArrayList for observer pattern*/
        observers = new ArrayList<ItemObserver>() ;
        
        this.current = category;
        this.display();
    }
      
    private GroceryController()
    {}
    
    public static GroceryController getInstance()
    {
        if(gc == null)
        {
            gc = new GroceryController();
            Inventory.invetoryConfig();
            return gc;
        }
        else
        {
            return gc;
        }
    }
    
   public void screenConfig()
   {
       
   }
    
   public void setScreen(GroceryScreen gs)
   {
       this.current = gs;
       display();
   }
   public void display()
   {
       current.display();
   }
   public void touch(int i)
   {
       current.touch(i);
    }
   public void checkMyCart()
   {
       if(this.current != mycart)
       {
//            if(dairy.myNewCart() != null)
//            {
//               mycart = dairy.myNewCart();
//               gocart = new ScreenCommand(mycart);
//               gocart.execute();
//               history.add(mycart);
//             }

           if(observers != null)
           {
              mycart = (MyCart) observers.get(0);
              gocart = new ScreenCommand(mycart);
              gocart.execute();
              history.add(mycart);
            }
            else
            {
                gocart.execute();
                history.add(mycart);
            }
        }
   }
   public void goToHome()
   {
       if(this.current != category)
       {
            gocategory.execute();
            history.add(category);
        }
   }
   public void goDairy()
   {
       if(this.current != dairy)
       {
           godairy.execute();
           history.add(dairy);
        }
    }
   public void goBakery()
   {
       if(this.current != bakery)
       {
           gobakery.execute();
           history.add(bakery);
       }
    }
   public void goVeg()
   {
       if(this.current != veg)
       {
           goveg.execute();
           history.add(veg);
        }
    }
   public void goWine()
   {
       if(this.current != wine)
       {
           gowine.execute();
           history.add(wine);
        }
   }
   public void goCheckOut()
   {
       if(this.current != checkout)
       {
           gocheckout.execute();
           history.add(checkout);
        }
   }
        
   public void type(int j)
   {
       current.type(j);
   }
   public void backspace()
   {
       current.backspace();
    }
   public void checkOut()
   {
       current.checkOut();
    }
   public void goBack()
   {
       if(history.size() > 1)
       {
            history.remove(history.size()-1);
            this.setScreen(history.get(history.size()-1));
       }
       else
       {
           if(this.current != category)
           this.setScreen(category);
        }
    }
    
    public MyCart getMyCart()
    {
        return mycart;
    }
    
    public void clearHistory()
    {
        history.clear();
    }
    
    public void setObservers(ItemObserver o)
    {
        observers.add(o);
        System.out.println("my cart registered to arraylist"+ observers.size());
    }
    
    public void clearObservers()
    {
        observers.clear();
    }
    
    public ArrayList<ItemObserver> getObserver()
    {
        return observers;
    }
}
