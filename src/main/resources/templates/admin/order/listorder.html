<!-- Header-->
<?php include './views/layout/header.php' ?>
<!-- EndHeader-->

<!-- Navbar -->
<?php include './views/layout/navbar.php' ?>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<?php include './views/layout/sidebar.php' ?>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <section class="content-header">
    <div class="container-fluid">
      <div class="row mb-2">
        <div class="col-sm-6">
          <h1><a
              href="<?= BASE_URL_ADMIN . '?act=listOrder' ?>">Quản
              Lý Đơn Hàng</a></h1>
        </div>
      </div>
    </div><!-- /.container-fluid -->
  </section>

  <!-- Main content -->
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <!-- /.card-header -->
            <div class="card-body">
              <table class="table table-bordered dataTable table-striped" style="text-align:center;">
                <thead>
                  <tr>
                    <th>Mã đơn hàng</th>
                    <th>Tài khoản ID</th>
                    <th>Tên người nhận</th>
                    <th>Số điện thoại</th>
                    <th>Địa chỉ</th>
                    <th>Tổng số lượng</th>
                    <th>Tổng tiền</th>
                    <th>Ngày đặt</th>
                    <th>Trạng thái</th>
                    <th>Chức năng</th>
                  </tr>
                </thead>
                <tbody>
                  <?php foreach ($listOrder as $order): ?>
                  <tr>
                    <td>
                      <?= $order['order_id'] ?>
                    </td>
                    <td>
                      <?= $order['tk_id'] ?>
                    </td>
                    <td>
                      <?= $order['ten_nhan'] ? $order['ten_nhan'] : $order['ho_ten'] ?>
                    </td>
                    <td>
                      <?= $order['sdt_nhan'] ? $order['sdt_nhan'] : $order['sdt']  ?>
                    </td>
                    <td>
                      <?= $order['dia_chi_nhan'] ? $order['dia_chi_nhan'] : $order['dia_chi']  ?>
                    </td>
                    <td>
                      <?= $order['tong_so_luong'] ?>
                    </td>
                    <td>
                      <?= number_format($order['tong_tien']) ?>
                    </td>
                    <td>
                      <?= date('d/m/Y H:i', strtotime($order['ngay_dat'])) ?>
                    </td>
                    <td>
                      <?php if($order['trang_thai'] == 1) {
                          echo'<p class="alert alert-info">Chờ xác nhận</p>';
                      } elseif($order['trang_thai'] == 2) {
                          echo'<p class="alert alert-warning">Đang giao hàng</p>';
                      } elseif($order['trang_thai'] == 3) {
                          echo'<p class="alert alert-success">Đã giao hàng</p>';
                      } else {
                          echo'<p class="alert alert-danger">Đơn bị hủy</p>';
                      } ?>
                    </td>
                    <td>
                      <a
                        href="<?= BASE_URL_ADMIN.'?act=update-trang-thai&id='.$order['order_id'] ?>"><button
                          class="btn btn-primary">Cập nhật</button></a>
                      <a
                        href="<?= BASE_URL_ADMIN.'?act=detailOrder&id='.$order['order_id'] ?>"><button
                          class="btn btn-info mt-2">Chi tiết</button></a>
                    </td>

                  </tr>
                  <?php endforeach ?>
                </tbody>
                <tfoot>
                  <tr>
                    <th>Mã đơn hàng</th>
                    <th>Tài khoản ID</th>
                    <th>Tên người nhận</th>
                    <th>Số điện thoại</th>
                    <th>Địa chỉ</th>
                    <th>Tổng số lượng</th>
                    <th>Tổng tiền</th>
                    <th>Ngày đặt</th>
                    <th>Trạng thái</th>
                    <th>Chức năng</th>
                  </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.card-body -->


          </div>
          <!-- /.card -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </div>
    <!-- /.container-fluid -->
  </section>
  <!-- /.content -->
</div>
<!-- /.content-wrapper -->
<!-- Footer -->
<?php include './views/layout/footer.php' ?>
<!-- EndFooter -->
<!-- Page specific script -->
<script>
  $(function() {
    $("#example1").DataTable({
      "responsive": true,
      "lengthChange": false,
      "autoWidth": false,
      "buttons": ["copy", "csv", "excel", "pdf", "print", "colvis"]
    }).buttons().container().appendTo('#example1_wrapper .col-md-6:eq(0)');
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false,
      "responsive": true,
    });
  });
</script>
</body>

</html>