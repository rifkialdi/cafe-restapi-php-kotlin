<?php
    $db_host = 'localhost';
    $db_Uname = 'root';
    $db_Pass = '';
    $db_DbName = 'cafe';

    $koneksi = mysqli_connect($db_host, $db_Uname, $db_Pass, $db_DbName);

    if(!$koneksi) {
        die("Koneksi Gagal!!" .mysqli_connect_error());
    }

?>