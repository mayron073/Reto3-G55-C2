package Vistas;

import Controller.EnumDepartament;
import Controller.EnumTypeStreet;
import Controller.EnumZone;
import Model.Conexion;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserMenu extends javax.swing.JFrame {
    // Objeto de la clase "Conexion".
    Conexion conexion = new Conexion();
    // Objeto de la clase "Connection", jdbc.
    Connection connection;
    Statement st;
    ResultSet rs;
    DefaultTableModel contentTable, contentTableDep;
    ComboBoxModel enumDepartament, enumZone, enumTypeStreet;
    
    public UserMenu() {
        enumDepartament = new DefaultComboBoxModel(EnumDepartament.values());
        enumZone = new DefaultComboBoxModel(EnumZone.values());
        enumTypeStreet = new DefaultComboBoxModel(EnumTypeStreet.values());
        initComponents();
        this.setLocationRelativeTo(this);
        this.insertEmployee();
        this.insertDepartament();
    }
    
    // Metodo para agregar empleados a la base de datos.
    public void insertEmployee(){
        // Quey consulta "SELECT".
        String query = "SELECT * FROM empleado;";
        
        // Validar conexion con la base de datos.
        try {
            connection = conexion.getConnection();
            // Crear consulta.
            st = connection.createStatement();
            rs = st.executeQuery(query);
            // Crear objeto para guardar los registros de usuario. 
            Object [] employee = new Object[6];
            contentTable = (DefaultTableModel)tblShowEmpleados.getModel();
            // Iterar el numero de usuarios y agreagarlos a la "tblShowEmpleados".
            while(rs.next()){
                // Agregar atributos al objeto "employee" con los valores de los usuarios de la db.
                employee[0] = rs.getInt("idEmpleados");
                employee[1] = rs.getString("nombre");
                employee[2] = rs.getString("apellido");
                employee[3] = rs.getString("tipoDocumento");
                employee[4] = rs.getString("documento");
                employee[5] = rs.getString("email");
                // Agregar a la tabla una fila nueva con los datos del objeto "employee".
                contentTable.addRow(employee);
                tblShowEmpleados.setModel(contentTable);
                contentTable.fireTableDataChanged();
                
                System.out.println(
                        "id: "+ employee[0] 
                        +"\nnombre: "+ employee[1]
                        +"\n"+ employee[2]
                        +"\ndocumento: "+ employee[3]
                        +"\nemail: "+ employee[4]);
            }
            
        } catch(SQLException e){
            System.out.println("no ha sido posible actualizar datos");
        }
    }
    
    // Metodo para eliminar empleados de la tblShowEmpleados.
    public void removeEmployee(){
        for (int i = 0; i < tblShowEmpleados.getRowCount(); i++){
            contentTable.removeRow(i);
            i = i - 1;
        }
    }

    // Metodo para agregar empleados a la tabla, por busqueda personalizada.
    public void insertSearchEmployee(String item){
        // actualizar informacion de la tabla.
        this.removeEmployee();
        // declarar variables locales.
        String search = txtSearchUser.getText();       
        // validar si la busqueda sera por una columna diferente a la de "id".
        if (!item.equals("idEmpleados")) {
            // Quey consulta "SELECT".
            String query = "SELECT * FROM empleado WHERE "+item+" Like '%"+search+"%';";       
            // Validar conexion con la base de datos.
            try {
                connection = conexion.getConnection();
                // Crear consulta.
                st = connection.createStatement();
                rs = st.executeQuery(query);
                // Crear objeto para guardar los registros de usuario. 
                Object [] employee = new Object[6];
                contentTable = (DefaultTableModel)tblShowEmpleados.getModel();
                // Iterar el numero de usuarios y agreagarlos a la "tblShowEmpleados".
                while(rs.next()){
                    // Agregar atributos al objeto "employee" con los valores de los usuarios de la db.
                    employee[0] = rs.getInt("idEmpleados");
                    employee[1] = rs.getString("nombre");
                    employee[2] = rs.getString("apellido");
                    employee[3] = rs.getString("tipoDocumento");
                    employee[4] = rs.getString("documento");
                    employee[5] = rs.getString("email");
                    // Agregar a la tabla una fila nueva con los datos del objeto "employee".
                    contentTable.addRow(employee);
                    tblShowEmpleados.setModel(contentTable);
                //    contentTable.fireTableDataChanged();
                
                    System.out.println(
                        "id: "+ employee[0] 
                        +"\nnombre: "+ employee[1]
                        +"\n"+ employee[2]
                        +"\ndocumento: "+ employee[3]
                        +"\nemail: "+ employee[4]);
                }
            
            } catch(SQLException e){
                System.out.println("no ha sido posible actualizar datos");
            }
        } else { // Buscar empleados por la columna "id".
            // Parseo.
            //int id = Integer.parseInt(item);
            int searchId = Integer.parseInt(search);
            // Quey consulta "SELECT".
            String query = "SELECT * FROM empleado WHERE "+item+" = '"+searchId+"';";        
            // Validar conexion con la base de datos.
            try {
                connection = conexion.getConnection();
                // Crear consulta.
                st = connection.createStatement();
                rs = st.executeQuery(query);
                // Crear objeto para guardar los registros de usuario. 
                Object [] employee = new Object[6];
                contentTable = (DefaultTableModel)tblShowEmpleados.getModel();
                // Iterar el numero de usuarios y agreagarlos a la "tblShowEmpleados".
                while(rs.next()){
                    // Agregar atributos al objeto "employee" con los valores de los usuarios de la db.
                    employee[0] = rs.getInt("idEmpleados");
                    employee[1] = rs.getString("nombre");
                    employee[2] = rs.getString("apellido");
                    employee[3] = rs.getString("tipoDocumento");
                    employee[4] = rs.getString("documento");
                    employee[5] = rs.getString("email");
                    // Agregar a la tabla una fila nueva con los datos del objeto "employee".
                    contentTable.addRow(employee);
                    tblShowEmpleados.setModel(contentTable);
                    contentTable.fireTableDataChanged();
                
                    System.out.println(
                        "id: "+ employee[0] 
                        +"\nnombre: "+ employee[1]
                        +"\n"+ employee[2]
                        +"\ndocumento: "+ employee[3]
                        +"\nemail: "+ employee[4]);
                }
            
            } catch(SQLException e){
                System.out.println("error de consulta po id");
            }
        } 
    }
    
    // Metodo para agregar direcciones.
    public void insertDepartament(){
        String query = "SELECT nombreSucursal, nombreDepartamento FROM sucursal INNER JOIN direccion ON FK_idDireccion = idDireccion GROUP BY nombreDepartamento, nombreSucursal ORDER BY nombreDepartamento;";
        
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Object [] departament = new Object[2];
            contentTableDep = (DefaultTableModel)tblShowSucursal.getModel();
            
            while (rs.next()){
                departament[0] = rs.getString("sucursal.nombreSucursal");
                departament[1] = rs.getString("direccion.nombreDepartamento");
                contentTableDep.addRow(departament);
                tblShowSucursal.setModel(contentTableDep);
            }
        } catch(SQLException e){
            System.out.println(e);
        }
    }
    
    // Metodo para eliminar departamentos
    public void removeDepartament(){
        for (int i = 0; i < tblShowSucursal.getRowCount(); i++){
            contentTableDep.removeRow(i);
            i = i - 1;
        }
    }
    
    // Metodo para actualizar tabla.
    public void updateTbl(){
        this.removeEmployee();
        this.insertEmployee();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        boxDepartament = new javax.swing.JComboBox<>();
        boxZone = new javax.swing.JComboBox<>();
        boxStreet = new javax.swing.JComboBox<>();
        txtNumer1 = new javax.swing.JTextField();
        txtNumer2 = new javax.swing.JTextField();
        txtNumer3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtSearch1 = new javax.swing.JTextField();
        btnSearch1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblShowSucursal = new javax.swing.JTable();
        btnShowEmployee = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShowEmpleados = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnUpdateWindow = new javax.swing.JButton();
        boxElection = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        txtSearchUser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        jTabbedPane1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Departamentos  con ");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("sucursales activas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(382, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(228, 228, 228));

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel4.setText("Departamento");

        jLabel6.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel6.setText("Zona");

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel7.setText("Tipo calle");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Registro de direccion");

        boxDepartament.setBackground(new java.awt.Color(228, 228, 253));
        boxDepartament.setModel(enumDepartament);

        boxZone.setBackground(new java.awt.Color(228, 228, 253));
        boxZone.setModel(enumZone);

        boxStreet.setBackground(new java.awt.Color(228, 228, 253));
        boxStreet.setModel(enumTypeStreet);

        txtNumer3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumer3ActionPerformed(evt);
            }
        });

        jLabel9.setText("N°");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("-");

        btnSave.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(boxZone, javax.swing.GroupLayout.Alignment.LEADING, 0, 145, Short.MAX_VALUE)
                            .addComponent(boxDepartament, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boxStreet, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumer1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumer2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumer3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(btnSave)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(boxDepartament, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(38, 38, 38)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(37, 37, 37)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(boxStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNumer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(55, 55, 55)
                .addComponent(btnSave)
                .addGap(49, 49, 49))
        );

        jPanel7.setBackground(new java.awt.Color(228, 228, 228));

        btnSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assests/search1.png"))); // NOI18N
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        tblShowSucursal.setBackground(new java.awt.Color(228, 228, 253));
        tblShowSucursal.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblShowSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sucursal", "Departamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblShowSucursal);

        btnShowEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assests/employee1.png"))); // NOI18N
        btnShowEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowEmployeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnShowEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShowEmployee)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sucursales", jPanel4);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mision TIC store ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Empleados");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assests/employee1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAddUser.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assests/addUser1.png"))); // NOI18N
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        tblShowEmpleados.setBackground(new java.awt.Color(228, 228, 253));
        tblShowEmpleados.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblShowEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido(s)", "Tipo documento", "Documento", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblShowEmpleados.setSelectionBackground(new java.awt.Color(204, 255, 255));
        tblShowEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShowEmpleadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblShowEmpleados);

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        btnUpdateWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assests/update.png"))); // NOI18N
        btnUpdateWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateWindowActionPerformed(evt);
            }
        });

        boxElection.setBackground(new java.awt.Color(228, 228, 253));
        boxElection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Identificacion", "ID" }));

        btnSearch.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assests/search1.png"))); // NOI18N
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel5.setText("Consulta por: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxElection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddUser)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateWindow)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnUpdateWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 32, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(boxElection, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Empleados", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("tab2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        // Crear un objeto de la clase "AddUser" para hacer visible la ventana de registro de usuario.
        AddUser addUser = new AddUser();
        addUser.setVisible(true);
        // Actualizar informacion de la tabla.        
        this.updateTbl();
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnUpdateWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateWindowActionPerformed
        this.updateTbl();
    }//GEN-LAST:event_btnUpdateWindowActionPerformed

    // Evento al presionar una fila de la tabla.
    private void tblShowEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShowEmpleadosMouseClicked
        // Declarar una variable(row) que gurade el numero de la fila seleccionada.
        int row = tblShowEmpleados.getSelectedRow();       
        // Validar si se selecciono una fila.
        if (row < 0){
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Menu",JOptionPane.WARNING_MESSAGE);
        } else {
            // declarar variables que guarden los datos de clada columna de la fila que se selecciono.
            int id = Integer.parseInt(tblShowEmpleados.getValueAt(row, 0).toString());
            String nombre = (String)tblShowEmpleados.getValueAt(row, 1);
            String apellidos = (String)tblShowEmpleados.getValueAt(row, 2);
            String tipoDocumento = (String)tblShowEmpleados.getValueAt(row, 3);
            String documento = (String)tblShowEmpleados.getValueAt(row, 4);
            String email = (String)tblShowEmpleados.getValueAt(row, 5);
            
            // Objeto de la clase "InfoUser".
            InfoUser infoUser = new InfoUser();
            infoUser.showInfoUser(id, nombre, apellidos, tipoDocumento ,documento, email);
            infoUser.setVisible(true);
            // Actualizar la tabla.
            this.updateTbl();
                   
        }
    }//GEN-LAST:event_tblShowEmpleadosMouseClicked

    // evento para consultas especificas.
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // declarar variables locales.
        String election = (String)boxElection.getSelectedItem();
        String search = txtSearchUser.getText();
        // Validar si el campo de busqueda esta vacio.
        if (search.isEmpty()){
            this.updateTbl();
        } else { //Identificar el tipo de busqueda.
            switch (election) {
            case "Nombre":                              
                this.insertSearchEmployee("nombre");
                break;
            case "Identificacion":
                this.insertSearchEmployee("documento");
                break;
            case "ID":
                this.insertSearchEmployee("idEmpleados");
                break;
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtNumer3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumer3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumer3ActionPerformed

    private void btnShowEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowEmployeeActionPerformed
        int numRow = tblShowSucursal.getSelectedRow();
        
        if (numRow != -1){
            String sucursal = (String)tblShowSucursal.getValueAt(numRow, 0);
            String departament = (String)tblShowSucursal.getValueAt(numRow, 1);
            String query = "SELECT idSucursal FROM `sucursal` WHERE nombreSucursal = '"+sucursal+"';";
            
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(query);
                EmployeeList employeeList = new EmployeeList(this, true);
                employeeList.setVisible(true);
                
                while (rs.next()){
                    int idSucursal = rs.getInt("idSucursal");
                    employeeList.reciveIdSucursal(idSucursal);
                }
            } catch(SQLException e){
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sucursal para continuar","",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnShowEmployeeActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        String departamentOption = boxDepartament.getSelectedItem().toString();
        String typeZone = boxZone.getSelectedItem().toString();
        String typeStreet = boxStreet.getSelectedItem().toString();
        String numer1 = txtNumer1.getText();
        String numer2 = txtNumer2.getText();
        String numer3 = txtNumer3.getText();
        
        String query = "INSERT INTO `direccion` (`zona`, `tipoCalle`, `numero1`, `numero2`, `numero3`, `nombreDepartamento`) VALUES ('"+typeZone+"','"+typeStreet+"','"+numer1+"','"+numer2+"','"+numer3+"','"+departamentOption+"');";
        
        try {
            connection = conexion.getConnection();
            st = connection.createStatement();
            st.executeUpdate(query);
            NewSucursal newSucursal = new NewSucursal(this, true);
            newSucursal.setVisible(true);
            String querySlect = "SELECT `idDireccion` FROM `direccion` WHERE nombreDepartamento = '"+departamentOption+"' AND zona = '"+typeZone+"' AND tipoCalle = '"+typeStreet+"' AND numero1 = '"+numer1+"' AND numero2 = '"+numer2+"' AND numero3 = '"+numer3+"';";
            
            try {
                connection = conexion.getConnection();
                st = connection.createStatement();
                rs = st.executeQuery(querySlect);
                int idDireccion;
                while (rs.next()){
                    idDireccion = rs.getInt("idDireccion");
                    newSucursal.reciveIdDireccion(idDireccion);
                }
                removeDepartament();
                insertDepartament();
            } catch (SQLException e){
                System.out.println(e);
            }
            JOptionPane.showMessageDialog(this, "Ha creado una nueva sucursal");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "No ha sido posible crear una nueva sucursal","",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxDepartament;
    private javax.swing.JComboBox<String> boxElection;
    private javax.swing.JComboBox<String> boxStreet;
    private javax.swing.JComboBox<String> boxZone;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnShowEmployee;
    private javax.swing.JButton btnUpdateWindow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblShowEmpleados;
    private javax.swing.JTable tblShowSucursal;
    private javax.swing.JTextField txtNumer1;
    private javax.swing.JTextField txtNumer2;
    private javax.swing.JTextField txtNumer3;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtSearchUser;
    // End of variables declaration//GEN-END:variables
}
