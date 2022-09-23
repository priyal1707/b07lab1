import java.lang.Math;
import java.util.Arrays;

public class Polynomial{
	/*  
	It has one field representing the coefficients of the polynomial using an array of 
	double. A polynomial is assumed to have the form 𝑎0 +𝑎1𝑥^1 +⋯+𝑎𝑛−1𝑥^𝑛−1. 
	For  example,  the  polynomial  6−2𝑥+5𝑥3  would  be  represented  using  the 
	array [6, -2, 0, 5] 
	*/ 
	double coef[]; 
	
	/*It  has  a  no-argument  constructor  that  sets  the  polynomial  to  zero  (i.e.  the 
			corresponding array would be [0]) */ 
	public Polynomial() {
		coef = new double[1];
		coef[0] = 0;
	}
	
	/*It  has  a  constructor  that  takes  an  array  of  double  as  an  argument  and  sets  the 
  	coefficients accordingly */ 
	public Polynomial(double [] a) {
		coef = new double[a.length];
		for (int i = 0; i < coef.length; i++) {
			coef[i] = a[i];
		}
		
	}
	
	/* It  has  a  method  named  add  that  takes  one  argument  of  type  Polynomial  and 
	returns the polynomial resulting from adding the calling object and the argument */

	
	public Polynomial add(Polynomial other) {
		int length = coef.length < other.coef.length ? other.coef.length : coef.length; 
		double[] result = new double[length];
		for (int i = 0; i < length; i++) { 
			if (i > coef.length - 1){
			result[i] = other.coef[i] + 0; 
			} 
			
			else if (i > other.coef.length - 1) {

				result[i] = 0 + coef[i]; 
			}
			else {
				result[i] = other.coef[i] + coef[i]; 
			}
		}
		return new Polynomial(result);
		
	}
	
	/* It  has  a  method  named  evaluate  that  takes  one  argument  of  type  double 
	representing a value of x and evaluates the polynomial accordingly. For example, 
	if the polynomial is 6−2𝑥+5𝑥3 and evaluate(-1) is invoked, the result should 
	be 3. */
	public double evaluate(double x) {
		/* coef[0] + coef[1]* x^2 */ 
		double fin = 0; 
		for (int i = 0; i < coef.length ; i++) {
			fin = fin + (coef[i]* Math.pow(x, i)) ;
		}
		return fin; 
	}
	
	/* It  has  a  method  named  hasRoot  that  takes  one  argument  of  type  double  and 
	determines whether this value is a root of the polynomial or not. Note that a root 
	is a value of x for which the polynomial evaluates to zero. */
	
	public boolean hasRoot(double x) {
		double fin = evaluate(x);  
		if (fin == 0) {
			return true; 
		}
		else {
			return false; 
		}
	}
	
}