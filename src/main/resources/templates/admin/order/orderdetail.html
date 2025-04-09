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
        <div>
        </div>
      </div><!-- /.container-fluid -->
  </section>

  <!-- Main content -->
  <section class="content">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <?php
                if ($detailDonHang['trang_thai'] == 1) {
                    $colorAlert = 'info'; // chờ xác nhận
                } elseif ($detailDonHang['trang_thai'] == 2) {
                    $colorAlert = 'warning'; // đang giao
                } elseif ($detailDonHang['trang_thai'] == 3) {
                    $colorAlert = 'success'; // đã giao
                } else {
                    $colorAlert = 'danger'; // đã hủy
                }
?>
          <div class="alert alert-<?=$colorAlert?>" role="alert">
            <?php
  if ($detailDonHang['trang_thai'] == 1) {
      echo'Trạng thái đơn hàng: Chờ xác nhận';
  } elseif ($detailDonHang['trang_thai'] == 2) {
      echo'Trạng thái đơn hàng: Đang giao';
  } elseif ($detailDonHang['trang_thai'] == 3) {
      echo'Trạng thái đơn hàng: Đã giao';
  } else {
      echo'Trạng thái đơn hàng: Hủy đơn';
  }
?>
          </div>


          <!-- Main content -->
          <div class="invoice p-3 mb-3">
            <!-- title row -->
            <div class="row">
              <div class="col-12">
                <h4> Shop sản phẩm giữ nhiệt BAĐ
                  <small class="float-right">Ngày tạo:
                    <?=$detailDonHang['ngay_tao']?></small>
                </h4>
              </div>
              <!-- /.col -->
            </div>
            <!-- info row -->
            <div class="row invoice-info">
              <div class="col-sm-4 invoice-col">
                Thông tin tài khoản đặt
                <address>
                  <strong><?= $detailDonHang['ho_ten'] ?></strong><br>
                  Email:
                  <?= $detailDonHang['email'] ?>
                  <br>
                  Phone:
                  <?= $detailDonHang['sdt_nhan'] ?><br>
                </address>
              </div>
              <!-- /.col -->
              <div class="col-sm-4 invoice-col">
                Người nhận
                <address>
                  <strong><?= $detailDonHang['ten_nhan'] ? $detailDonHang['ten_nhan'] : $detailDonHang['ho_ten'] ?></strong><br>
                  Phone:
                  <?= $detailDonHang['sdt_nhan'] ? $detailDonHang['sdt_nhan'] : $detailDonHang['sdt'] ?><br>
                  Địa chỉ:
                  <?= $detailDonHang['dia_chi_nhan'] ? $detailDonHang['dia_chi_nhan'] : $detailDonHang['dia_chi'] ?>
                </address>
              </div>
              <!-- /.col -->
              <div class="col-sm-4 invoice-col">
                <b>Mã Đơn hàng:</b>
                DH<?= $detailDonHang['order_id'] ?><br>
                <b>Tổng số lượng:</b>
                <?= $detailDonHang['tong_so_luong'] ?>
                - Sản phẩm<br>
                <b>Tổng tiền:</b>
                <?= number_format($detailDonHang['tong_tien']) ?>
                VNĐ
              </div>
              <!-- /.col -->
            </div>
            <!-- /.row -->

            <!-- Table row -->
            <div class="row">
              <div class="col-12 table-responsive">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Mã đơn hàng</th>
                      <th>Tên sản phẩm</th>
                      <th>Size</th>
                      <th>Giá mua</th>
                      <th>Số lượng</th>
                    </tr>
                  </thead>
                  <tbody>
                    <?php
      //  var_dump($productDonHang);
      foreach ($productDonHang as $sanpham) { ?>
                    <tr>
                      <td>
                        <?= $sanpham['ctdh_id'] ?>
                      </td>
                      <td>
                        <?= $sanpham['order_id'] ?>
                      </td>
                      <td>
                        <?= $sanpham['ten_sp'] ?>
                      </td>
                      <td>
                        <?= $sanpham['size_value'] ?>
                      </td>
                      <td>
                        <?= $sanpham['gia_mua'] ?>
                        VNĐ</td>
                      <td>
                        <?= $sanpham['so_luong_mua'] ?>
                      </td>
                    </tr>
                    <?php }; ?>
                  </tbody>
                </table>
              </div>
              <!-- /.col -->
            </div>
            <!-- /.row -->

            <!-- /.row -->

            <!-- this row will not appear when printing -->
            <div class="row no-print">
              <div class="col-12">
                <a href="invoice-print.html" rel="noopener" target="_blank" class="btn btn-default"><i
                    class="fas fa-print"></i> Print</a>
                <button type="button" class="btn btn-success float-right"><i class="far fa-credit-card"></i> Submit
                  Payment
                </button>
                <button type="button" class="btn btn-primary float-right" style="margin-right: 5px;">
                  <i class="fas fa-download"></i> Generate PDF
                </button>
              </div>
            </div>
          </div>
          <!-- /.invoice -->
        </div><!-- /.col -->
      </div><!-- /.row -->
    </div><!-- /.container-fluid -->
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