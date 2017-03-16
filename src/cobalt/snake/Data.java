package cobalt.snake;

import java.util.LinkedList;

public class Data{
	Site egg=null;
	Character command='r';
	Integer score=0;
	LinkedList<Site> snake=null;
	
	/**
	 * ������������һ��Data���󣬳�ʼ��һ������
	 */
	public Data() {
		super();
		this.snake=new LinkedList<Site>();
		//�涨��snake�����β����ɾ����ͷ��
		this.snake.add(new Site(0,0));
		//����eggֱ����snakeλ�ò�ͬ
		while((this.egg=Site.randomSite(Const.MAX_HEIGHTS, Const.MAX_WIDTH)).equals(this.snake.getFirst()));
		this.score=0;
	}
	/**
	 * ���ģ�һ���غϵ���Ϊ,�ɹ�ִ�з���true
	 * һ���غ϶���Ϊ��һ�����ݿ������������UI֮ǰ�Ĳ���
	 * @throws ͨ���׳�GameOverException�쳣�ķ�ʽ���������Ϸ
	 */
	public boolean round() throws GameOverException{
		//��ȡ�����̵߳�����
		char original=this.command;
		this.command=Input.getCommand();
			
		boolean isDead=false;
		boolean isSnake=true;
		Site tail=null;
		Site head=null;
		synchronized(this.snake){
			//ɾ��ĩβ���ж��Ƿ�����
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
			//�ж��Ƿ�Ե��˵�
			if(head.equals(egg)){
				score++;
				this.snake.addFirst(tail);
				Site newEgg=null;
				//ѭ����ֱ������Ϊ��
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
 * ̰����-�����أ��洢����
 * @author Cobalt-YangFan
 * 
 */
class Const{
	//���������������0��n-1
	public static final int MAX_HEIGHTS=30;
	public static final int MAX_WIDTH=30;
}
/**
 * �洢���ݵĵ�Ԫ
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
	//�ж�Site�Ƿ���ȵı�ݷ���
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
	//�����������һ��Site�ĺ�����ָ�����x�����y
	public static Site randomSite(int max_X,int max_Y){
		int x=(int)(Math.random()*(double)max_X);
		int y=(int)(Math.random()*(double)max_Y);
		return new Site(x,y);
	}
	//���ڿ�ݸı����Site��ֵ
	public Site change(char status,char original){
		Site other=new Site(this.x,this.y);
		//������Data
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
	//�ж��Ƿ�Խ��
	public boolean isIn(int max_X,int max_Y){
		boolean b=true;
		b&=this.x>=0;
		b&=this.y>=0;
		b&=this.x<max_X;
		b&=this.x<max_Y;
		return b;
	}
}