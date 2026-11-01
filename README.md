# ☕ Kopi dan Nyemil

Aplikasi **Android native (Kotlin + Jetpack Compose)** untuk memesan kopi dan makanan ringan, dibuat berdasarkan wireframe tugas. Aplikasi ini memakai **data statis (dummy)** — tanpa database.

> _Get a taste of your wish._

## Deskripsi

Kopi dan Nyemil adalah aplikasi pemesanan sederhana. Pengguna mendaftar atau login, memilih menu makanan/minuman, mengisi alamat pengiriman, lalu mendapat konfirmasi pesanan. Seluruh data (akun, menu, keranjang, pesanan) disimpan di memori selama aplikasi berjalan.

## Fitur

Sesuai wireframe, ada 8 layar:

| No | Layar | Keterangan |
|----|-------|------------|
| 1 | Splash / Welcome | Logo, nama, tagline, tombol **Start now** |
| 2 | Sign up | Pilihan **Register** atau **Login** |
| 3 | Login User | Form username & password dengan validasi |
| 4 | Register | Form nama lengkap, username, password (validasi + cek username unik) |
| 5 | Home | Sapaan "Halo {nama}", daftar **7 menu**, filter kategori, tambah/kurangi jumlah, bar keranjang |
| 6 | Profile | Ubah nama (Save) + Logout |
| 7 | Alamat pengiriman (Order) | Nama, alamat, patokan, ringkasan pesanan & total, tombol **Order dan Kirim** |
| 8 | Konfirmasi pesanan | Pesan terima kasih + rincian pesanan, tombol **Oke** |

Pengembangan tambahan dari wireframe:

- Desain modern bertema kopi (Material 3, warna cokelat/krem).
- Bottom navigation (Home / Order / Profile).
- Keranjang: tambah/kurangi jumlah item, total harga otomatis dalam format Rupiah.
- Filter kategori Semua / Minuman / Makanan.
- Tombol "Order dan Kirim" aktif hanya jika keranjang & alamat terisi.

### Akun demo

| Username | Password |
|----------|----------|
| `kopi` | `123456` |

Atau buat akun baru lewat halaman **Register**.

## Teknologi

- **Kotlin** 1.9.24
- **Jetpack Compose** (BOM 2024.09.00) + **Material 3**
- **Navigation Compose** 2.8.0
- **ViewModel** (state di memori)
- Android Gradle Plugin 8.5.2, minSdk 24, targetSdk 34

## Struktur Project

```
app/src/main/java/com/kopidannyemil/app/
├── MainActivity.kt
├── data/            # Model, data dummy, AppViewModel
├── navigation/      # NavHost, rute, bottom navigation
└── ui/
    ├── theme/       # Warna & tema
    ├── components/  # Tombol, text field, logo
    └── screens/     # 8 layar aplikasi
```

## Cara Menjalankan

1. Install **Android Studio** (Koala atau lebih baru) dengan **JDK 17** (bawaan Android Studio sudah cukup).
2. Clone repository:
   ```bash
   git clone https://github.com/<username>/<nama-repo>.git
   ```
3. Buka folder project di Android Studio (**File → Open**), tunggu **Gradle Sync** selesai.
4. Pilih emulator (API 24+) atau hubungkan HP Android dengan USB debugging aktif.
5. Klik **Run ▶** .

## Screenshot

Taruh screenshot di folder `docs/screenshots/` lalu sesuaikan nama file di bawah.

| Splash | Sign up | Login | Register |
|:--:|:--:|:--:|:--:|
| ![Splash](docs/screenshots/01-splash.png) | ![Sign up](docs/screenshots/02-signup.png) | ![Login](docs/screenshots/03-login.png) | ![Register](docs/screenshots/04-register.png) |

| Home | Profile | Alamat | Konfirmasi |
|:--:|:--:|:--:|:--:|
| ![Home](docs/screenshots/05-home.png) | ![Profile](docs/screenshots/06-profile.png) | ![Alamat](docs/screenshots/07-alamat.png) | ![Konfirmasi](docs/screenshots/08-konfirmasi.png) |

## Pembuat

- Nama: _isi nama & NIM_
- Mata kuliah: _isi mata kuliah_
