using System;
using System.Diagnostics;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;

namespace ChickensRevenge
{
	/// <summary>
	/// This is the main type for your game.
	/// </summary>
	public class ChickensRevenge : Game
	{
		GraphicsDeviceManager graphics;
		SpriteBatch spriteBatch;
		const float MOVEMENT_DELAY = 1000;

		public Vector2 foxLocation;
		public Vector2 chickenLocation;
		public int[,] map { get; set; }

		private Texture2D poule, renard, herbe, mur, caisse, egg, cross;
		private const int TILE_SIZE = 16;
		private const int MAP_SIZE = 23;
		private bool keyIsPressed = false;
		private float elapsedTime = 0;
		private GameState gameState = GameState.PLAYING;
		private SpriteFont fontBig, fontSmall;

		private enum Direction { LEFT, RIGHT, UP, DOWN }
		private enum GameState { PLAYING, GAME_WIN, GAME_LOST }

		public ChickensRevenge()
		{
			graphics = new GraphicsDeviceManager(this);
			graphics.PreferredBackBufferWidth = MAP_SIZE * TILE_SIZE;
			graphics.PreferredBackBufferHeight = MAP_SIZE * TILE_SIZE;
			Content.RootDirectory = "Content";
		}

		/// <summary>
		/// Allows the game to perform any initialization it needs to before starting to run.
		/// This is where it can query for any required services and load any non-graphic
		/// related content.  Calling base.Initialize will enumerate through any components
		/// and initialize them as well.
		/// </summary>
		protected override void Initialize()
		{
			foxLocation = new Vector2(1, 1);
			chickenLocation = new Vector2(11, 11);
			map = new int[,]
			{
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			};

			base.Initialize();
		}

		/// <summary>
		/// LoadContent will be called once per game and is the place to load
		/// all of your content.
		/// </summary>
		protected override void LoadContent()
		{
			// Create a new SpriteBatch, which can be used to draw textures.
			spriteBatch = new SpriteBatch(GraphicsDevice);

			poule = Content.Load<Texture2D>("poule");
			renard = Content.Load<Texture2D>("renard");
			egg = Content.Load<Texture2D>("egg");
			cross = Content.Load<Texture2D>("cross");
			herbe = Content.Load<Texture2D>("herbe");
			mur = Content.Load<Texture2D>("wall");
			caisse = Content.Load<Texture2D>("caisse");
			fontBig = Content.Load<SpriteFont>("FontBig");
			fontSmall = Content.Load<SpriteFont>("FontSmall");
		}

		/// <summary>
		/// Allows the game to run logic such as updating the world,
		/// checking for collisions, gathering input, and playing audio.
		/// </summary>
		/// <param name="gameTime">Provides a snapshot of timing values.</param>
		protected override void Update(GameTime gameTime)
		{
			// Si on appuy sur ECHAP, on quitte le jeu
			if (Keyboard.GetState().IsKeyDown(Keys.Escape))
			{
				Exit();
			}

			// Boucle de jeu (Jeu en cours)
			if (gameState == GameState.PLAYING)
			{
				// Calcul du temps écouler depuis le dernier déplacement du renard
				elapsedTime += (float)gameTime.ElapsedGameTime.TotalMilliseconds;

				// Si le renard n'a pas bougé depuis un temps supérieur au délais voullu (1s), on le fait bouger
				if (elapsedTime >= MOVEMENT_DELAY)
				{
					elapsedTime = 0;
					moveFox();
				}


				// Gestion du clavier
				// Récupération de toute les touches appuyées
				Keys[] keyPresed = Keyboard.GetState().GetPressedKeys();

				// Si aucune touche n'est appuyée on réinitialise le marqueur évitant le déplacement continue
				if (keyPresed.Length == 0)
				{
					keyIsPressed = false;
				}

				if (Keyboard.GetState().IsKeyDown(Keys.Down) && chickenLocation.Y > 0 && !keyIsPressed)
				{
					move(Direction.DOWN);
				}

				if (Keyboard.GetState().IsKeyDown(Keys.Up) && chickenLocation.Y < MAP_SIZE - 1 && !keyIsPressed)
				{
					move(Direction.UP);
				}

				if (Keyboard.GetState().IsKeyDown(Keys.Left) && chickenLocation.X > 0 && !keyIsPressed)
				{
					move(Direction.LEFT);
				}

				if (Keyboard.GetState().IsKeyDown(Keys.Right) && chickenLocation.X < MAP_SIZE - 1 && !keyIsPressed)
				{
					move(Direction.RIGHT);
				}
			}
			else
			{
				if (Keyboard.GetState().IsKeyDown(Keys.Enter))
				{
					Initialize();
					gameState = GameState.PLAYING;
				}

			}

			base.Update(gameTime);
		}

		// Déplacement du renard
		private void moveFox()
		{
			Vector2 newLocation = getFoxPath(foxLocation, chickenLocation);

			// Le renard mange la poule ?
			if (newLocation == chickenLocation)
			{
				gameState = GameState.GAME_LOST;


				// Le renard est bloquer ?
			}
			else if (newLocation == foxLocation)
			{
				gameState = GameState.GAME_WIN;

				// Si le renard bouge 
			}
			else
			{
				foxLocation = newLocation;
			}
		}

		// Calcule de la distance entre 2 points
		private int distance(float xa, float ya, float xb, float yb)
		{
			return (int)Math.Round(Math.Sqrt(Math.Pow(xb - xa, 2) + Math.Pow(yb - ya, 2)));
		}

		// Calcul du chemin à prendre (cacul basique regardant uniquement les cases autour du renard)
		private Vector2 getFoxPath(Vector2 foxPosition, Vector2 chickenPosition)
		{
			int bestValue = 1000;
			int currentValue;
			Vector2 bestPosition = foxPosition;

			// N
			if (foxPosition.Y > 0 && map[(int)foxPosition.Y - 1, (int)foxPosition.X] == 1)
			{
				currentValue = distance(foxPosition.X, foxPosition.Y - 1, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X, foxPosition.Y - 1);
				}
			}

			// E
			if (foxPosition.X < MAP_SIZE && map[(int)foxPosition.Y, (int)foxPosition.X + 1] == 1)
			{
				currentValue = distance(foxPosition.X + 1, foxPosition.Y, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X + 1, foxPosition.Y);
				}
			}

			// S
			if (foxPosition.Y < MAP_SIZE && map[(int)foxPosition.Y + 1, (int)foxPosition.X] == 1)
			{
				currentValue = distance(foxPosition.X, foxPosition.Y + 1, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X, foxPosition.Y + 1);
				}
			}

			// O
			if (foxPosition.X > 0 && map[(int)foxPosition.Y, (int)foxPosition.X - 1] == 1)
			{
				currentValue = distance(foxPosition.X - 1, foxPosition.Y, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X - 1, foxPosition.Y);
				}
			}

			// NE
			if (foxPosition.X < MAP_SIZE && foxPosition.Y > 0 && map[(int)foxPosition.Y - 1, (int)foxPosition.X + 1] == 1)
			{
				currentValue = distance(foxPosition.X + 1, foxPosition.Y - 1, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X + 1, foxPosition.Y - 1);
				}
			}

			// SE
			if (foxPosition.X < MAP_SIZE && foxPosition.Y < MAP_SIZE && map[(int)foxPosition.Y + 1, (int)foxPosition.X + 1] == 1)
			{
				currentValue = distance(foxPosition.X + 1, foxPosition.Y + 1, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X + 1, foxPosition.Y + 1);
				}
			}

			// SO
			if (foxPosition.X > 0 && foxPosition.Y < MAP_SIZE && map[(int)foxPosition.Y + 1, (int)foxPosition.X - 1] == 1)
			{
				currentValue = distance(foxPosition.X - 1, foxPosition.Y + 1, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X - 1, foxPosition.Y + 1);
				}
			}

			// NO
			if (foxPosition.X > 0 && foxPosition.Y > 0 && map[(int)foxPosition.Y - 1, (int)foxPosition.X - 1] == 1)
			{
				currentValue = distance(foxPosition.X - 1, foxPosition.Y - 1, chickenPosition.X, chickenPosition.Y);
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestPosition = new Vector2(foxPosition.X - 1, foxPosition.Y - 1);
				}
			}


			return bestPosition;
		}

		// Déplacement de la poule (si c'est possible) selon la touche pressée
		private void move(Direction direction)
		{
			if (canMove(chickenLocation.X, chickenLocation.Y, direction))
			{
				int cellValue;

				keyIsPressed = true;

				switch (direction)
				{
					case Direction.LEFT:
						cellValue = map[(int)chickenLocation.Y, (int)(chickenLocation.X - 1)];
						if (cellValue == 2) pushBox((int)(chickenLocation.X - 1), (int)chickenLocation.Y, Direction.LEFT);
						chickenLocation.X -= 1;
						break;
					case Direction.RIGHT:
						cellValue = map[(int)chickenLocation.Y, (int)(chickenLocation.X + 1)];
						if (cellValue == 2) pushBox((int)(chickenLocation.X + 1), (int)chickenLocation.Y, Direction.RIGHT);
						chickenLocation.X += 1;
						break;
					case Direction.UP:
						cellValue = map[(int)(chickenLocation.Y - 1), (int)chickenLocation.X];
						if (cellValue == 2) pushBox((int)chickenLocation.X, (int)(chickenLocation.Y - 1), Direction.UP);
						chickenLocation.Y -= 1;
						break;
					case Direction.DOWN:
						cellValue = map[(int)(chickenLocation.Y + 1), (int)chickenLocation.X];
						if (cellValue == 2) pushBox((int)chickenLocation.X, (int)(chickenLocation.Y + 1), Direction.DOWN);
						chickenLocation.Y += 1;
						break;
				}
			}
		}

		// Déplacement des caisses
		private void pushBox(int x, int y, Direction direction)
		{
			switch (direction)
			{
				case Direction.LEFT:
					if (map[y, x - 1] == 2) pushBox(x - 1, y, Direction.LEFT);
					map[y, x - 1] = 2;
					map[y, x] = 1;
					break;
				case Direction.RIGHT:
					if (map[y, x + 1] == 2) pushBox(x + 1, y, Direction.RIGHT);
					map[y, x + 1] = 2;
					map[y, x] = 1;
					break;
				case Direction.UP:
					if (map[y - 1, x] == 2) pushBox(x, y - 1, Direction.UP);
					map[y - 1, x] = 2;
					map[y, x] = 1;
					break;
				case Direction.DOWN:
					if (map[y + 1, x] == 2) pushBox(x, y + 1, Direction.DOWN);
					map[y + 1, x] = 2;
					map[y, x] = 1;
					break;
			}
		}

		// Vérification de la possibilité de déplacer la poule dans la direction souhaitée
		private bool canMove(float x, float y, Direction direction)
		{
			int cellValue;

			switch (direction)
			{
				case Direction.LEFT:
					cellValue = map[(int)y, (int)(x - 1)];
					if (cellValue == 0 || (foxLocation.X.Equals(x - 1) && foxLocation.Y.Equals(y))) return false;
					else if (cellValue == 1) return true;
					else if (cellValue == 2) return canMove(x - 1, y, Direction.LEFT);
					break;
				case Direction.RIGHT:
					cellValue = map[(int)y, (int)(x + 1)];
					if (cellValue == 0 || (foxLocation.X.Equals(x + 1) && foxLocation.Y.Equals(y))) return false;
					else if (cellValue == 1) return true;
					else if (cellValue == 2) return canMove(x + 1, y, Direction.RIGHT);
					break;
				case Direction.UP:
					cellValue = map[(int)(y - 1), (int)x];
					if (cellValue == 0 || (foxLocation.X.Equals(x) && foxLocation.Y.Equals(y - 1))) return false;
					else if (cellValue == 1) return true;
					else if (cellValue == 2) return canMove(x, y - 1, Direction.UP);
					break;
				case Direction.DOWN:
					cellValue = map[(int)(y + 1), (int)x];
					if (cellValue == 0 || (foxLocation.X.Equals(x) && foxLocation.Y.Equals(y + 1))) return false;
					else if (cellValue == 1) return true;
					else if (cellValue == 2) return canMove(x, y + 1, Direction.DOWN);
					break;
			}

			return false;
		}

		/// <summary>
		/// This is called when the game should draw itself.
		/// </summary>
		/// <param name="gameTime">Provides a snapshot of timing values.</param>
		protected override void Draw(GameTime gameTime)
		{
			graphics.GraphicsDevice.Clear(Color.CornflowerBlue);

			spriteBatch.Begin();

			for (int j = 0; j < MAP_SIZE; j++)
			{
				for (int i = 0; i < MAP_SIZE; i++)
				{
					if (map[i, j] == 1)
					{
						spriteBatch.Draw(herbe, new Vector2(j * TILE_SIZE, i * TILE_SIZE), Color.White);
					}
					else if (map[i, j] == 2)
					{
						spriteBatch.Draw(caisse, new Vector2(j * TILE_SIZE, i * TILE_SIZE), Color.White);
					}
					else
					{
						spriteBatch.Draw(mur, new Vector2(j * TILE_SIZE, i * TILE_SIZE), Color.White);
					}
				}
			}

			if (gameState == GameState.GAME_WIN)
			{
				spriteBatch.Draw(poule, new Vector2(chickenLocation.X * TILE_SIZE, chickenLocation.Y * TILE_SIZE), Color.White);
				spriteBatch.Draw(egg, new Vector2(foxLocation.X * TILE_SIZE, foxLocation.Y * TILE_SIZE), Color.White);
				spriteBatch.DrawString(fontBig, "YOU WIN !!!", new Vector2(80, 100), Color.White);
				spriteBatch.DrawString(fontSmall, "PRESS \"ENTER\" TO RESTART", new Vector2(50, 150), Color.White);
				spriteBatch.DrawString(fontSmall, "PRESS \"ECHAP\" TO QUIT", new Vector2(70, 170), Color.White);
			}
			else if (gameState == GameState.GAME_LOST)
			{
				spriteBatch.Draw(cross, new Vector2(chickenLocation.X * TILE_SIZE, chickenLocation.Y * TILE_SIZE), Color.White);
				spriteBatch.Draw(renard, new Vector2(foxLocation.X * TILE_SIZE, foxLocation.Y * TILE_SIZE), Color.White);
				spriteBatch.DrawString(fontBig, "GAME OVER !", new Vector2(65, 100), Color.White);
				spriteBatch.DrawString(fontSmall, "PRESS \"ENTER\" TO RESTART", new Vector2(50, 150), Color.White);
				spriteBatch.DrawString(fontSmall, "PRESS \"ECHAP\" TO QUIT", new Vector2(70, 170), Color.White);
			}
			else
			{
				spriteBatch.Draw(poule, new Vector2(chickenLocation.X * TILE_SIZE, chickenLocation.Y * TILE_SIZE), Color.White);
				spriteBatch.Draw(renard, new Vector2(foxLocation.X * TILE_SIZE, foxLocation.Y * TILE_SIZE), Color.White);
			}

			spriteBatch.End();

			base.Draw(gameTime);
		}
	}
}
