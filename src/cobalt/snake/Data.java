package cobalt.snake;

import java.util.LinkedList;

public class Data{
	Site egg=null;
	Character command='r';
	Integer score=0;
	LinkedList<Site> snake=null;
	
	/**
	 * 构造器，生成一个Data对象，初始化一切数据
	 */
	public Data() {
		super();
		this.snake=new LinkedList<Site>();
		//规定：snake增添从尾部，删除从头部
		this.snake.add(new Site(0,0));
		//建立egg直到与snake位置不同
		while((this.egg=Site.randomSite(Const.MAX_HEIGHTS, Const.MAX_WIDTH)).equals(this.snake.getFirst()));
		this.score=0;
	}
	/**
	 * 核心：一个回合的行为,成功执行返回true
	 * 一个回合定义为从一次数据库操作到反馈至UI之前的部分
	 * @throws 通过抛出GameOverException异常的方式结束这个游戏
	 */
	public boolean round() throws GameOverException{
		//读取输入线程的数据
		char original=this.command;
		this.command=Input.getCommand();
			
		boolean isDead=false;
		boolean isSnake=true;
		Site tail=null;
		Site head=null;
		synchronized(this.snake){
			//删除末尾，判断是否死亡
			head=this.snake.getLast().change(command,original);
			tail=this.snake.removeFirst();		
			for(Site s:this.snake){
				isDead|=s.equals(head);
			}
			isDead|=!head.isIn(Const.MAX_HEIGHTS, Const.MAX_WIDTH);
			if(isDead){
				throw new GameOverException(score.toString());
			}
			this.snake.addLast(head);
			//判断是否吃到了蛋
			if(head.equals(egg)){
				score++;
				this.snake.addFirst(tail);
				Site newEgg=null;
				//循环，直到蛋不为蛇
				for(;isSnake;){
					newEgg=Site.randomSite(Const.MAX_HEIGHTS, Const.MAX_WIDTH);
					isSnake=false;
					for(Site s:this.snake){
						isSnake|=s.equals(newEgg);
					}
				}
				this.egg=newEgg;
			}
		}
		return true;
	}
	
}
/**
 * 贪吃蛇-常量池，存储常量
 * @author Cobalt-YangFan
 * 
 */
class Const{
	//最大行数列数，从0到n-1
	public static final int MAX_HEIGHTS=30;
	public static final int MAX_WIDTH=30;
}
/**
 * 存储数据的单元
 * @author Cobalt-YangFan
 *
 */
class Site{
	int x;
	int y;
	
	public Site(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	//判断Site是否相等的便捷方法
	@Override
	public boolean equals(Object other){
		boolean flag=false;
		if(other!=null&&other instanceof Site){
			Site s=(Site)other;
			if(s.x==this.x&&s.y==this.y){
				flag=true;
			}
		}
		return flag;
	}
	//用于随机生成一个Site的函数，指定最大x与最大y
	public static Site randomSite(int max_X,int max_Y){
		int x=(int)(Math.random()*(double)max_X);
		int y=(int)(Math.random()*(double)max_Y);
		return new Site(x,y);
	}
	//用于快捷改变这个Site的值
	public Site change(char status,char original){
		Site other=new Site(this.x,this.y);
		//不操作Data
		switch(status){
		case 'u':{
			if(original!='d')
				other.x--;
			break;
		}
		case 'd':{
			if(original!='u')
				other.x++;
			break;
		}
		case 'l':{
			if(original!='r')
				other.y--;
			break;
		}
		case 'r':{
			if(original!='l')
				other.y++;
			break;
		}
		}
		return other;
	}
	//判断是否越界
	public boolean isIn(int max_X,int max_Y){
		boolean b=true;
		b&=this.x>=0;
		b&=this.y>=0;
		b&=this.x<max_X;
		b&=this.x<max_Y;
		return b;
	}
}