import java.util.Scanner;

abstract class GameObject {
	protected int distance, x, y;
	
	public GameObject(int startX, int startY, int distance) {
		this.x=startX;
		this.y=startY;
		this.distance=distance;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean collide(GameObject p) {
		if(this.x==p.getX() && this.y==p.getY())
			return true;
		else
			return false;
	}
	protected void move() {}
	protected abstract char getShape();
	
}
//����
class Zombie extends GameObject {
	
	//������ ���� ����
	boolean life_flag=true;
	
	public Zombie(int startX, int startY, int distance) {
		super(startX, startY, distance);
	}
	protected void move(int A) {
		switch(A) {
		case 1:
			if(x<10-distance)
				x+=distance;
			break;
		case 2:
			if(y<20-distance)
				y+=distance;
			break;
		case 3:
			if(x>distance)
				x-=distance;
			break;
		case 4:
			if(y>distance)
				y-=distance;
			break;
		}
	}
	protected char getShape() {	return 'Z'; }
}

//������
class Survivor extends GameObject {
	
	public Survivor(int startX, int startY, int distance) {
		super(startX, startY, distance);
	}
	
	protected void move(String A) {
		switch(A) {
		case "s":
			if(x<10-distance)
				x+=distance;
			break;
		case "d":
			if(y<20-distance)
				y+=distance;
			break;
		case "w":
			if(x>distance)
				x-=distance;
			break;
		case "a":
			if(y>distance)
				y-=distance;
			break;
		}
	}
	protected char getShape() { return 'S'; }
	
	//�Ѿ��� ���񿡰� �¾Ҵ��� �Ǵ��ϴ� �Լ�
	void kill(Zombie p, String selection) {
		if(getX()<p.getX() && getY()==p.getY() && selection.equals("2"))
			p.life_flag=false;
		if(getX()>p.getX() && getY()==p.getY() && selection.equals("8"))
			p.life_flag=false;
		if(getX()==p.getX() && getY()>p.getY() && selection.equals("4"))
			p.life_flag=false;
		if(getX()==p.getX() && getY()<p.getY() && selection.equals("6"))
			p.life_flag=false;
	}
	
	//����� ������ ���� ��ȯ�ϴ� �Լ�
	int score(Zombie p1, Zombie p2, Zombie p3) {
		int score=0;
		if(p1.life_flag==false)
			score++;
		if(p2.life_flag==false)
			score++;
		if(p3.life_flag==false)
			score++;
		
		return score;
	}
}


public class Bear_Hunter {

	public static void main(String[] args) {
		
		String selection;
		Scanner sc=new Scanner(System.in);
		
		Zombie z1=new Zombie(6, 7, 1);
		Zombie z2=new Zombie(3, 5, 2);
		Zombie z3=new Zombie(4, 19, 3);

		/*
		Zombie z1=new Zombie((int)(Math.random()*10+1), (int)(Math.random()*20+1), 1);
		Zombie z2=new Zombie((int)(Math.random()*10+1), (int)(Math.random()*20+1), 1);
		Zombie z3=new Zombie((int)(Math.random()*10+1), (int)(Math.random()*20+1), 1);
		*/
		
		Survivor s=new Survivor(7, 14, 1);
		
		System.out.println("<���۹�>");
		System.out.println("�̵�: a(����) d(������) w(����) s(�Ʒ���)");
		System.out.println("����: 4(����) 6(������) 8(����) 2(�Ʒ���)\n");
		System.out.println("��Ƴ����ʽÿ�!");
		
		//�������� ����� �����ڰ� �������� �Ǵ�
		while(!((s.collide(z1) && z1.life_flag==true) || (s.collide(z2) && z2.life_flag==true) || (s.collide(z3) && z3.life_flag==true))) {
			
			
			//�� �ݺ����� ���� 
			for(int i=0; i<10; i++) {
				for(int j=0; j<20; j++) {
					//���� ǥ��
					if(i==z1.getX() && j==z1.getY() && z1.life_flag==true)
						System.out.print(z1.getShape());
					else if(i==z2.getX() && j==z2.getY() && z2.life_flag==true)
						System.out.print(z2.getShape());
					else if(i==z3.getX() && j==z3.getY() && z3.life_flag==true)
						System.out.print(z3.getShape());
					//������ ǥ��
					else if(i==s.getX() && j==s.getY())
						System.out.print(s.getShape());
					
					else
						System.out.print('-');
				}
				System.out.println("");
			}
			
			//������� ����
			selection=sc.next();
					
			//���� ���� �����ߴٸ�, �Ѿ��� �¾Ҵ��� �Ǵ�
			if(selection.equals("2") || selection.equals("4") || selection.equals("6") || selection.equals("8")) {
				s.kill(z1, selection);
				s.kill(z2, selection);
				s.kill(z3, selection);
			}
			//����� �̵�
			else 
				s.move(selection);
			//���� �̵�
			z1.move((int)(Math.random()*4+1));
			z2.move((int)(Math.random()*4+1));
			z3.move((int)(Math.random()*4+1));
		
			if(z1.life_flag==false && z2.life_flag==false && z3.life_flag==false)
				break;
		}
		
		if(z1.life_flag==false && z2.life_flag==false && z3.life_flag==false)
			System.out.println("����� ��Ƴ��ҽ��ϴ�");
		else
			System.out.println("����� ���� �Ǿ����ϴ�");
		
		System.out.println("����� ���� ��:"+s.score(z1, z2, z3));
	}

}
