import java.util.ArrayList;

public class Matrix implements MatrixOperations {

    public Field F;
    public VectorSpace V;
    public Double[][] A;

    public Matrix(Field f, VectorSpace v, int rows, int collums){
        this.F = f;
        this.V = v;
        this.A = new Double[rows][collums];
    }
    public Matrix(Double[][] Arr,Field f, VectorSpace v){
        this.F = f;
        this.V = v;
        // deep copy the given array
        try {
            this.A = new Double[Arr.length][Arr[0].length];
            for (int i = 0; i < Arr.length; i++) {
                for (int j = 0; j < Arr[0].length; j++) {
                    this.A[i][j] = Arr[i][j];
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Trying to assert a null array into matrix");
        }
    }
    // multiplies A * B if reverse = = false
    // multiplies B * A if reverse == true
    @Override
    public Matrix Multiplication(Matrix B, boolean reverse) {
        if(!B.F.equals(this.F) || !B.V.equals(this.V)) return null;
        Double[][] left = reverse ? B.A : A;
        Double[][] right = reverse ? A : B.A;
        Double[][] leftXright = new Double[left[0].length][right.length];
        // cannot multiply when collum length isnt equal to row length
        if(left[0].length != right.length) return null;
        int[] leftMatrixSize = {left.length,left[0].length},
        rightMatrixSize = {right.length, right[0].length};
        for (int i = 0; i < leftMatrixSize[0]; i++) {
            for (int j = 0; j < leftMatrixSize[1]; j++) {
                leftXright[i][j] = Double.valueOf(left[i][j] * right[j][i]);
            }
        }
        Matrix ret = new Matrix(leftXright,B.F,B.V);
        return ret;
    }

    @Override
    public double Determinant() {
        Matrix matrix = new Matrix(this.A, F, V);
        if(matrix.A.length != matrix.A[0].length) return -999999999;
        return Determinant(matrix,0);
    }
    private double Determinant(Matrix matrix, double curr){
        Double[][] curmat = matrix.A;
        if(curmat.length == 2 && curmat[0].length == 2){
            return ( (curmat[0][0] * curmat[1][1]) - (curmat[0][1] * curmat[1][0]) );
        }
        double sum = curr;
        double scalar;
        for (int i = 0; i < curmat.length; i++) {
            scalar = Math.pow(-1,i) * curmat[i][0];
            sum +=  scalar * Determinant(Minor(matrix, i, 0), curr);
        }
        return sum;
    }
    private Matrix Minor(Matrix beforeMatrix, int rowindex, int collindex){
        Double[][] curmat = beforeMatrix.A;
        Double[][] minorMatrix = new Double[curmat.length][curmat[0].length-1];
        if(curmat.length != curmat[0].length){
            return null;
        }
        int currentRow = 0, currentCollum = 0;
        for (int i = 0; i < curmat.length; i++) {
            if(i != rowindex)
            for (int j = 0; j < curmat[0].length; j++) {
                if( j != collindex){
                    minorMatrix[currentRow][currentCollum++] = curmat[i][j];
                }
            }
            currentCollum = 0;
        }
        Matrix minor = new Matrix(minorMatrix,beforeMatrix.F,beforeMatrix.V);
        return minor;
    }
    @Override
    public Matrix addition(Matrix B) {
        if(this.F != B.F || this.V != B.V) return null;
        if((B.A.length != this.A.length) || (B.A[0].length != this.A[0].length)) return null;
        Double[][] matrix = new Double[B.A.length][this.A.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = this.A[i][j] + B.A[i][j];
            }
        }
        return new Matrix(matrix,B.F,B.V);
    }

    @Override
    public Matrix Transformation(Matrix B) {
        return null;
    }

    @Override
    public Matrix Transpose() {
        Double[][] transMatrix = new Double[this.A[0].length][this.A.length];
        for (int i = 0; i < this.A.length; i++) {
            for (int j = 0; j < this.A[0].length; j++) {
                transMatrix[j][i] = this.A[i][j];
            }
        }
        return new Matrix(transMatrix,this.F,this.V);
    }

    @Override
    public Matrix GausianElimination() {
        Double[][] gausMatrix = new Double[this.A.length][this.A[0].length];
        // init matrix
        for (int i = 0; i < gausMatrix.length; i++) {
            for (int j = 0; j < gausMatrix[0].length; j++) {
                gausMatrix[i][j] = this.A[i][j];
            }
        }
        // start of the algorithm
        // running on the trace of the matrix
        for (int currentMember = 0; currentMember < gausMatrix.length; currentMember++) {
            if(gausMatrix[currentMember][currentMember] != 1){
                gausMatrix[currentMember][currentMember] *= 1/gausMatrix[currentMember][currentMember];
            }
            for (int i = 0; i < gausMatrix.length; i++) {
                if(i != currentMember)
                for (int j = 0; j < gausMatrix[0].length; j++) {
                        //gausMatrix[i][j] = gausMatrix[i][j] - gausMatrix[currentMember][j];
                }
            }

        }
        return null;
    }

    @Override
    public Matrix Inverse() {

        return null;
    }

    @Override
    public Matrix MultiByScalar(double scalar) {
        Double[][] mat = new Double[this.A.length][this.A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                mat[i][j] = A[i][j] *scalar;
            }
        }
        Matrix ret= new Matrix(mat,this.F,this.V);
        return ret;
    }

    @Override
    public boolean Invertible() {
        double det = Determinant();
        return det != 0;
    }

    @Override
    public int Dimension() {
        return 0;
    }

    @Override
    public Matrix CoordinateMatrix(Matrix Base) {
        return null;
    }

    @Override
    public Matrix Base() {
        return null;
    }
}
