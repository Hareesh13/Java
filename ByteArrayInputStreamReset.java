import java.io.*;
class ByteArrayInputStreamReset {
public static void main(String args[]) throws IOException {
String tmp = "abc";
byte b[] = tmp.getBytes();
byte[] bbb=new byte[10];

System.out.println(bbb[0]);
System.out.println(b.length);
for(int i=0;i<=2;i++)
{
System.out.println(b[i]);

}
ByteArrayInputStream in = new ByteArrayInputStream(b);
for (int i=0; i<2; i++) 
{
int c;
while ((c = in.read()) != -1) {
if (i == 0) {
System.out.print((char) c);
} else {
System.out.print(Character.toUpperCase((char) c));
}
}
System.out.println();
in.reset();
}
}
}