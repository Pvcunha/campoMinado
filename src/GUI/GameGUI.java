package GUI;

import java.util.HashMap;
import java.util.Map;
import core.enums.Action;
import core.enums.EstadoZona;
import core.game.Game;
import core.game.Play;
import core.game.Position;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

public class GameGUI extends Application {
	
	private final int TILE_SIZE = 40;
	
	private final int W = 600;
	private final int H = 400;
	
	private Game game;
	
	private Stage gameStage;
	
	private Scene scene;
	
	private Pane root;
	
	private Map<Position, Tile> gridTile;
	
	private Label leftBombs;
	
	private void createContent() {
		leftBombs = new Label("Bombas: " + game.getGrid().getNumBombs());
		root = new Pane();
		root.setPrefSize(W, H);
		
		for(int i = 0; i < game.getGrid().getROW(); i++) {
			for(int j = 0; j < game.getGrid().getCOLUMN(); j++) {
				Position pos = new Position(j, i);
				Tile tile = new Tile(pos);
				this.gridTile.put(pos, tile);
				root.getChildren().add(tile);
			}
		}
		
		root.getChildren().add(leftBombs);
		leftBombs.setTranslateX(W - 100);
		leftBombs.setTranslateY(20);
		leftBombs.setTextFill(Color.BLACK);
		leftBombs.setFont(new Font(14));
	}
	
	public void updateNumBombs() {
		leftBombs.setText("Bombas: " + (game.getGrid().getNumBombs() - game.getCheckedBombs()));
	}
	
	public void updateGrid() {
		
		if(game.isInGame()) {
			
			game.getGrid().getGrid().forEach((p, c) -> {
				if(c.getState().equals(EstadoZona.OPEN)) {
					gridTile.get(p).open();
				} else if(c.getState().equals(EstadoZona.CHECKED)) {
					gridTile.get(p).check();
				} else {
					gridTile.get(p).close();
				}
			});
			
			updateNumBombs();
			gameStage.setScene(scene);
			gameStage.show();
		} else {
			if(game.checkWin()) {
				win();
			} else {
				lose();
			}
		}
	}
	
	private void win() {
		root.getChildren().clear();
		
		Rectangle bg = new Rectangle(W, H);
		Label lb = new Label("VOCE GANHOU!!!");
		
		bg.setFill(Color.BLACK);
		
		lb.setAlignment(Pos.CENTER);
		lb.setTextFill(Color.WHITE);
		root.getChildren().addAll(bg, lb);
		
		gameStage.setScene(scene);
		gameStage.show();
	}
	
	private void lose() {
		root.getChildren().clear();
		
		Rectangle bg = new Rectangle(W, H);
		Label lb = new Label("VOCE PERDEU!!!");
		
		bg.setFill(Color.BLACK);
		
		lb.setAlignment(Pos.CENTER);
		lb.setTextFill(Color.WHITE);
		root.getChildren().addAll(bg, lb);
		
		gameStage.setScene(scene);
		gameStage.show();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		gridTile = new HashMap<Position, Tile>();
		game = new Game();
		gameStage = new Stage();
		game.getGrid().print();
		createContent();
		this.scene = new Scene(root);
		
		gameStage.setTitle("Campo minado");
		gameStage.setScene(scene);
		gameStage.show();
	}
	

	public int getW() {
		return W;
	}

	public int getH() {
		return H;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}


	public static void main(String[] args) {
		launch(args);
	}


	public Stage getGameStage() {
		return gameStage;
	}


	public void setGameStage(Stage gameStage) {
		this.gameStage = gameStage;
	}
	
	private class Tile extends StackPane {
		private Position pos;
		private Text text;
		
		private Rectangle rect;
		
		public Tile(Position pos) {
			rect = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
			this.pos = pos;
			this.text = new Text();
			this.text.setText(game.getGrid().getBombs().contains(pos)? "X" : game.getGrid().getGrid().get(pos).getNearBombs().toString());
			text.setVisible(false);
			
			rect.setFill(Color.BLACK);
			rect.setStroke(Color.GRAY);
			
			getChildren().addAll(rect, text);
			
			setTranslateX(pos.getX() * TILE_SIZE);
			setTranslateY(pos.getY() * TILE_SIZE);
			
			setOnMouseClicked(e -> move(e));
		}
		
		public void move(MouseEvent e) {
			
			Play play = null;
			
			if(e.getButton().equals(MouseButton.PRIMARY))
				play = new Play(pos, Action.OPEN);
			else if(e.getButton().equals(MouseButton.SECONDARY))
				play = new Play(pos, Action.MARK);
			game.move(play);
			updateGrid();
		}
		
		public void open() {
			text.setVisible(true);
			rect.setFill(Color.ANTIQUEWHITE);
		}
		
		public void check() {
			rect.setFill(Color.YELLOW);
		}
		
		public void close() {
			rect.setFill(Color.BLACK);
		}
		
		@SuppressWarnings("unused")
		public Position getPos() {
			return pos;
		}
		
		@SuppressWarnings("unused")
		public void setPos(Position pos) {
			this.pos = pos;
		}
		
		@SuppressWarnings("unused")
		public Text getText() {
			return text;
		}
		
		@SuppressWarnings("unused")
		public void setText(Text text) {
			this.text = text;
		}

		@SuppressWarnings("unused")
		public Rectangle getRect() {
			return rect;
		}

		@SuppressWarnings("unused")
		public void setRect(Rectangle rect) {
			this.rect = rect;
		}
		
	}
	
	

}
