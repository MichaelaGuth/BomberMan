package main.mapgenerator;

import java.util.Random;

public class Map {
    private static float destructibleWall_Coef = 0.6f;

    private float[][] mapNoise;
    private MapBlock[][] map;
    private int size;
    private int flameLength;


    public Map(int size, int flameLength) {
        this.size = size;
        map = new MapBlock[size][size];

        this.flameLength = flameLength;

        generateEmptyMap();
        prepareNoiseMap();
    }

    private void prepareNoiseMap() {
        Random random = new Random();

        mapNoise = new float[size - 2][];
        for (int i = 0; i < size - 2; i++) {
            mapNoise[i] = new float[size - 2];

            for (int j = 0; j < size - 2; j++) {
                mapNoise[i][j] = (float) random.nextDouble() % 1;
            }
        }
    }

    private float[][] generateSmoothNoise(float[][] baseNoise, int octave) {
        int rows = baseNoise.length;
        int cols = baseNoise[0].length;

        float[][] smoothNoise = getEmptyArray(rows, cols);
        int samplePeriod = 1 << octave;
        float sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < rows; i++) {
            //calculate the horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % rows; //wrap around
            float horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < cols; j++) {
                //calculate the vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % rows; //wrap around
                float vertical_blend = (j - sample_j0) * sampleFrequency;

                //blend the top two corners
                float top = interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontal_blend);

                //blend the bottom two corners
                float bottom = interpolate(baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontal_blend);

                //final blend
                smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
            }
        }

        return smoothNoise;
    }

    private float interpolate(float x0, float x1, float alpha) {
        return x0 * (1 - alpha) + alpha * x1;
    }

    private float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing

        float persistance = 0.7f;

        //generate smooth noise
        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = getEmptyArray(width, height); //an array of floats initialised to 0

        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    private float[][] getEmptyArray(int rows, int cols) {
        float[][] result = new float[rows][];

        for (int i = 0; i < rows; i++) {
            result[i] = new float[cols];
        }

        return result;
    }

    private void generateEmptyMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    map[i][j] = MapBlock.INDESTRUCTIBLE_BLOCK;
                } else {
                    if (i % 2 == 0 && j % 2 == 0) {
                        map[i][j] = MapBlock.INDESTRUCTIBLE_BLOCK;
                    } else {
                        map[i][j] = MapBlock.NONE;
                    }
                }
            }
        }
    }

    private void mapPostprocessing() {
        map[1][1] = map[1][size - 2] = map[size - 2][1] = map[size - 2][size - 2] = MapBlock.NONE;

        spawnCorrection(1, 1, flameLength);
        spawnCorrection(1, size - 2, flameLength);
        spawnCorrection(size - 2, 1, flameLength);
        spawnCorrection(size - 2, size - 2, flameLength);
    }

    private void spawnCorrection(int x, int y, int flameLength) {
        boolean left, right, top, bottom;
        left = right = top = bottom = false;
        if (map[x - 1][y] != MapBlock.INDESTRUCTIBLE_BLOCK) {
            left = isSurvivable(x - 1, y, -1, 0, flameLength);
            if (left) {
                map[x - 1][y] = MapBlock.NONE;
            }
        }

        if (map[x + 1][y] != MapBlock.INDESTRUCTIBLE_BLOCK) {
            right = isSurvivable(x + 1, y, 1, 0, flameLength);
            if (right) {
                map[x + 1][y] = MapBlock.NONE;
            }
        }

        if (map[x][y + 1] != MapBlock.INDESTRUCTIBLE_BLOCK) {
            top = isSurvivable(x, y + 1, 0, 1, flameLength);
            if (top) {
                map[x][y + 1] = MapBlock.NONE;
            }
        }

        if (map[x][y - 1] != MapBlock.INDESTRUCTIBLE_BLOCK) {
            bottom = isSurvivable(x, y - 1, 0, -1, flameLength);
            if (bottom) {
                map[x][y - 1] = MapBlock.NONE;
            }
        }

        if (!(left | right | top | bottom)) {
            map[x - 1][y] = map[x - 1][y] != MapBlock.INDESTRUCTIBLE_BLOCK ? MapBlock.NONE : MapBlock.INDESTRUCTIBLE_BLOCK;
            map[x + 1][y] = map[x + 1][y] != MapBlock.INDESTRUCTIBLE_BLOCK ? MapBlock.NONE : MapBlock.INDESTRUCTIBLE_BLOCK;
            map[x][y - 1] = map[x][y - 1] != MapBlock.INDESTRUCTIBLE_BLOCK ? MapBlock.NONE : MapBlock.INDESTRUCTIBLE_BLOCK;
            map[x][y + 1] = map[x][y + 1] != MapBlock.INDESTRUCTIBLE_BLOCK ? MapBlock.NONE : MapBlock.INDESTRUCTIBLE_BLOCK;
        }
    }

    private boolean isSurvivable(int startX, int startY, int dirX, int dirY, int flameLength) {
        boolean result = false;

        int newPosX = startX + dirX;
        int newPosY = startY + dirY;
        int wayLength = 2;

        int[] lookLeft = new int[]{dirY * -1, dirX};
        int[] lookRight = new int[]{dirY, dirX * -1};

        while (map[newPosX][newPosY] == MapBlock.NONE) {
            wayLength++;
            if (map[newPosX + lookLeft[0]][newPosY + lookLeft[1]] == MapBlock.NONE) {
                result = true;
                break;
            }

            if (map[newPosX + lookRight[0]][newPosY + lookRight[1]] == MapBlock.NONE) {
                result = true;
                break;
            }
            newPosX += dirX;
            newPosY += dirY;
        }

        result = wayLength > flameLength ? true : result;

        return result;
    }

    public void generateMap(int octave) {
        mapNoise = generatePerlinNoise(mapNoise, octave);

        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                if (map[i][j] != MapBlock.INDESTRUCTIBLE_BLOCK && mapNoise[i - 1][j - 1] < destructibleWall_Coef) {
                    map[i][j] = MapBlock.DESTRUCTIBLE_BLOCK;
                }
            }
        }

        mapPostprocessing();
    }

    public String getNoiseMap() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mapNoise.length; i++) {
            for (int j = 0; j < mapNoise[0].length; j++) {
                sb.append(mapNoise[i][j] + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public char[][] getCharMap() {
        char[][] result = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = map[i][j].getSign();
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Map size is " + size + " x " + size + "\n\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(map[i][j].getSign());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
