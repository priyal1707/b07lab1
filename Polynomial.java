import java.lang.Math;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Polynomial {
	double coef[];
	int exp[]; 

	public Polynomial() {
		coef = new double[] {0};
		exp = new int[] {0};
	}

  public Polynomial(double[] c, int[] e) {
    coef = Arrays.copyOf(c, c.length);
    exp = Arrays.copyOf(e, e.length);
  }

  public Polynomial(File file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      line = line.replaceAll("-", "+-");
      if (line.charAt(0) == '+') line = line.substring(1); 
      String[] terms = line.split("\\+");
      coef = new double[terms.length];
      exp = new int[terms.length];
      for (int i = 0; i < terms.length; i++) {
        String[] parts = terms[i].split("x");
        coef[i] = Double.parseDouble(parts[0]);
        if (parts.length == 2)
          exp[i] = Integer.parseInt(parts[1]);
        else if (terms[i].indexOf('x') != -1)
          exp[i] = 1;
        else
          exp[i] = 0;
      }
      fr.close();
      br.close();
    } catch (IOException e) {
      coef = new double[] {0};
      exp = new int[] {0};
    }
  }

  private void sort() {
    double c[] = new double[coef.length];
    int e[] = new int[exp.length];
    int ee[] = Arrays.copyOf(exp, exp.length);
    Arrays.sort(ee);
    for (int i = 0; i < ee.length; i++) {
      int idx = 0;
      for (int j = 0; j < exp.length; j++, idx++) {
        if (ee[i] == exp[j]) {
          break;
        }
      }
      c[i] = coef[idx];
      e[i] = exp[idx];
    }
    coef = c;
    exp = e;
  }

  public Polynomial add(Polynomial other) {
    int l = exp.length + other.exp.length;
    double c[] = new double[l];
    int e[] = new int[l];

    sort();
    other.sort();
    int i = 0;
    int j = 0;
    int k = 0;
    while (i < coef.length && j < other.coef.length) {
      if (exp[i] == other.exp[j]) {
        c[k] = coef[i] + other.coef[j];
        e[k] = exp[i];
        i++;
        j++;
      } else if (exp[i] > other.exp[j]) {
        c[k] = other.coef[j];
        e[k] = other.exp[j];
        j++;
      } else {
        c[k] = coef[i];
        e[k] = exp[i];
        i++;
      }
      k++;
    }
    while (i < coef.length) {
      c[k] = coef[i];
      e[k++] = exp[i++];
    }
    while (j < other.coef.length) {
      c[k] = other.coef[j];
      e[k++] = other.exp[j++];
    }
    c = Arrays.copyOf(c, k);
    e = Arrays.copyOf(e, k);
    return new Polynomial(c, e);
  }

  public Polynomial multiply(Polynomial other) {
    double c[] = new double[other.coef.length];
    int e[] = new int[other.exp.length];
    Polynomial result = new Polynomial();
    for (int i = 0; i < coef.length; i++) {
      for (int j = 0; j < other.coef.length; j++) {
        c[j] = coef[i] * other.coef[j];
        e[j] = exp[i] + other.exp[j];
      }
      result = result.add(new Polynomial(c, e));
    }
    return result;
  }

  void saveToFile(String fileName) {
    sort();
    try {
      File file = new File(fileName);
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < coef.length; i++) {
        if (coef[i] > 0 && i > 0) {
          sb.append('+');
        }
        sb.append(Integer.toString((int)coef[i]));
        if (exp[i] > 0) {
          sb.append("x");
          if (exp[i] > 1) {
            sb.append(Integer.toString(exp[i]));
          }
        }
      }
      bw.write(sb.toString());
      bw.close();
      fw.close();
    } catch (IOException e) {
    }
  }

  public double evaluate(double x) {
    double fin = 0;
    for (int i = 0; i < coef.length; i++) {
      fin = fin + (coef[i] * Math.pow(x, exp[i]));
     
    }
    return fin;
  }
  
  

  public boolean hasRoot(double x) {
    return evaluate(x) == 0;
  }
}