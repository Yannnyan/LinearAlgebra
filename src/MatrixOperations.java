public interface MatrixOperations {

    public Matrix Multiplication(Matrix B, boolean reverse);
    public Matrix MultiByScalar(double scalar);
    public double Determinant();
    public Matrix GausianElimination();
    public Matrix addition(Matrix B);
    public Matrix Transformation(Matrix B);
    public Matrix Transpose();
    public Matrix Inverse();
    public boolean Invertible();
    public int Dimension();
    public Matrix CoordinateMatrix(Matrix Base);
    public Matrix Base();



}
