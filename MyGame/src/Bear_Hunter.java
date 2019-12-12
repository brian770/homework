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
//좀비
class Zombie extends GameObject {
	
	//좀비의 생존 여부
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

//생존자
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
	
	//총알이 좀비에게 맞았는지 판단하는 함수
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
	
	//사살한 좀비의 수를 반환하는 함수
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
		
		System.out.println("<조작법>");
		System.out.println("이동: a(왼쪽) d(오른쪽) w(위쪽) s(아래쪽)");
		System.out.println("발포: 4(왼쪽) 6(오른쪽) 8(위쪽) 2(아래쪽)\n");
		System.out.println("살아남으십시오!");
		
		//죽지않은 좀비와 생존자가 만났는지 판단
		while(!((s.collide(z1) && z1.life_flag==true) || (s.collide(z2) && z2.life_flag==true) || (s.collide(z3) && z3.life_flag==true))) {
			
			
			//이 반복문은 현재 
			for(int i=0; i<10; i++) {
				for(int j=0; j<20; j++) {
					//좀비 표시
					if(i==z1.getX() && j==z1.getY() && z1.life_flag==true)
						System.out.print(z1.getShape());
					else if(i==z2.getX() && j==z2.getY() && z2.life_flag==true)
						System.out.print(z2.getShape());
					else if(i==z3.getX() && j==z3.getY() && z3.life_flag==true)
						System.out.print(z3.getShape());
					//생존자 표시
					else if(i==s.getX() && j==s.getY())
						System.out.print(s.getShape());
					
					else
						System.out.print('-');
				}
				System.out.println("");
			}
			
			//사용자의 선택
			selection=sc.next();
					
			//만약 총을 발포했다면, 총알이 맞았는지 판단
			if(selection.equals("2") || selection.equals("4") || selection.equals("6") || selection.equals("8")) {
				s.kill(z1, selection);
				s.kill(z2, selection);
				s.kill(z3, selection);
			}
			//사용자 이동
			else 
				s.move(selection);
			//좀비 이동
			z1.move((int)(Math.random()*4+1));
			z2.move((int)(Math.random()*4+1));
			z3.move((int)(Math.random()*4+1));
		
			if(z1.life_flag==false && z2.life_flag==false && z3.life_flag==false)
				break;
		}
		
		if(z1.life_flag==false && z2.life_flag==false && z3.life_flag==false)
			System.out.println("당신은 살아남았습니다");
		else
			System.out.println("당신은 좀비가 되었습니다");
		
		System.out.println("사살한 좀비 수:"+s.score(z1, z2, z3));
	}

}
