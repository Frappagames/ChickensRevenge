using System;

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

		public Vector2 foxLocation;
		public Vector2 chickenLocation;
		public int[,] map { get; set; }

		private Texture2D poule, renard, herbe, mur, caisse;
		private const int TILE_SIZE = 16;
		private const int MAP_SIZE = 21;
		private bool keyIsPressed = false;

		private enum Direction { LEFT, RIGHT, UP, DOWN }

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
			foxLocation = new Vector2 (1, 1);
			chickenLocation = new Vector2(10, 10);
			map = new int[,]
			{
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{ 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
			herbe = Content.Load<Texture2D>("herbe");
			mur = Content.Load<Texture2D>("wall");
			caisse = Content.Load<Texture2D>("caisse");
		}

		/// <summary>
		/// Allows the game to run logic such as updating the world,
		/// checking for collisions, gathering input, and playing audio.
		/// </summary>
		/// <param name="gameTime">Provides a snapshot of timing values.</param>
		protected override void Update(GameTime gameTime)
		{

			if (Keyboard.GetState().IsKeyDown(Keys.Escape))
			{
				Exit();
			}
			
			Keys[] keyPresed = Keyboard.GetState().GetPressedKeys();

			if (keyPresed.Length == 0) {
				keyIsPressed = false;
			}

			if (Keyboard.GetState().IsKeyDown(Keys.Down) && chickenLocation.Y > 0 && !keyIsPressed) {
				move(Direction.DOWN);
			}

			if (Keyboard.GetState().IsKeyDown(Keys.Up) && chickenLocation.Y < MAP_SIZE - 1 && !keyIsPressed) {
				move(Direction.UP);
			}

			if (Keyboard.GetState().IsKeyDown(Keys.Left) && chickenLocation.X > 0 && !keyIsPressed) {
				move(Direction.LEFT);
			}

			if (Keyboard.GetState().IsKeyDown(Keys.Right) && chickenLocation.X < MAP_SIZE - 1 && !keyIsPressed) {
				move(Direction.RIGHT);
			}

			base.Update(gameTime);
		}

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

		private bool canMove(float x, float y, Direction direction)
		{
			int cellValue;

			switch (direction)
			{
				case Direction.LEFT:
					cellValue = map[(int)y, (int)(x - 1)];
					if (cellValue == 0 || (foxLocation.X.Equals(x - 1) && foxLocation.Y.Equals(y)))	return false;
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
					if (map[i, j] == 1) {
						spriteBatch.Draw(herbe, new Vector2(j * TILE_SIZE, i * TILE_SIZE), Color.White);
					} else if(map[i, j] == 2) {
						spriteBatch.Draw(caisse, new Vector2(j * TILE_SIZE, i * TILE_SIZE), Color.White);
					} else {
						spriteBatch.Draw(mur, new Vector2(j * TILE_SIZE, i * TILE_SIZE), Color.White);
					}
				}
			}

			spriteBatch.Draw(poule, new Vector2(chickenLocation.X * TILE_SIZE, chickenLocation.Y * TILE_SIZE), Color.White);
			spriteBatch.Draw(renard, new Vector2(foxLocation.X * TILE_SIZE, foxLocation.Y * TILE_SIZE), Color.White);

			spriteBatch.End();

			base.Draw(gameTime);
		}
	}
}
