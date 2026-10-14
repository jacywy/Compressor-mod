<div align="center">
  <img width="96" alt="ic_launcher-playstore" src="assets/logo.png?v=2" />

# Compressor Mod

Lightning fast, ad free, super lightweight native video compressor for Android.

**Fork of [JoshAtticus/Compressor](https://github.com/JoshAtticus/Compressor)** with extended features: in-app playback, original folder save, safe original video deletion & compact UI.

[English](#-enhanced-features--mod-highlights) | [中文说明](#-增强功能与改进特性)

<br>

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) 
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) 
![License](https://img.shields.io/badge/License-GPL--3.0-blue?style=for-the-badge)

<br>

<img src="assets/select.jpg" alt="Screenshot 3" width="24%"/> <img src="assets/settings.jpg" alt="Screenshot 1" width="24%"/> <img src="assets/compressing.jpg" alt="Screenshot 2" width="24%"> <img src="assets/done.jpg" alt="Screenshot 4" width="24%"/>
</div>

---

## 🌟 Enhanced Features / Mod Highlights

This fork enhances the original [Compressor](https://github.com/JoshAtticus/Compressor) with practical daily workflow improvements:

- 🎬 **Instant In-App Video Playback**: Preview compressed videos directly within the app using built-in Media3 ExoPlayer before saving, complete with playback controls and quick access to system players.
- 📁 **Save to Original Video Directory**: Option to save compressed outputs directly into the original video's folder (e.g. `DCIM/Camera/`), with one-tap toggle between original path and default folder (`Movies/Compressor`).
- 🗑️ **Safe Original Video Deletion**: Easily delete the large original video upon successful compression to instantly reclaim storage space. Includes an optional "Auto-delete original after saving" feature. Full Scoped Storage compatibility across Android 10 to Android 15.
- ⚡ **Zero-Scroll Compact Result Screen**: Completely redesigned result dashboard fitting standard mobile viewports without vertical scrolling. Unified storage management and seamless one-click authorization + auto-save flow.

---

## 🌟 增强功能与改进特性 (中文说明)

本项目基于开源项目 [JoshAtticus/Compressor](https://github.com/JoshAtticus/Compressor) 修改与增强，拓展了日常高频使用的实用功能与交互体验：

- 🎬 **压缩后即时播放预览**：压缩完成后可在应用内直接使用内置播放器流畅预览视频，快速核对压缩后的画质与音质，并支持一键调用系统外部播放器。
- 📁 **保存至原视频目录**：支持将压缩后的视频直接写入原视频所在目录（如 `DCIM/Camera/`），无需再手动移动相册文件；随时支持一键切回默认目录（`Movies/Compressor`）。
- 🗑️ **安全删除原视频释放空间**：对压缩效果满意后，可一键删除原视频释放手机空间，支持“保存后自动删除原视频”选项；全兼容 Android 10 至 Android 15 的 Scoped Storage 存储规范。
- ⚡ **单屏零滚动极简交互**：重构压缩结果页为极致紧凑单屏设计，无需上下滑动即可完成预览、保存路径选择、原文件删除及分享；支持主按钮一键授权并自动完成保存。

---

## Features
- Faster than every single compression app on the Play Store. Period.
- Uses native Media3 library, not another slow, bulky FFMpeg wrapper
- H.265 and AV1 support for compatible devices
- Share Sheet Support
- No third party libraries
- No invasive permissions (no storage, no internet etc)
- Ad free
- Completely native Kotlin (no React Native slop here)
- Simple, clean UI
- Works on Android 7.0 and up
- Reproducible Builds

---

## Performance
Below are four benchmarks of Compressor running on various devices. The baseline videos are available underneath all the benchmarks. All devices are running Compressor **1.6.3**.

> [!NOTE]
> **Testing Context & Methodology (v1.6.3 vs v1.5.2):**
> - **Thermal Conditions:** In the 1.5.2 benchmarks, devices were kept chilled in a cooler between runs. For 1.6.3, all tests were run at realistic **ambient room temperature (~21°C)** without active cooling to better reflect real-world use.
> - **Timing:** Timing was captured manually via stopwatch, so a small margin of error is expected.
> - **Comparison:** Comparisons against previous 1.5.2 results are included where the same device was re-tested.

<details>
<summary><b>Testing Devices</b></summary>
<br>
The following devices are used for testing where possible:

| Device | SoC | RAM |
|---|---|---|
| **Google Pixel 8 Pro** | Tensor G3 | 12GB |
| **Google Pixel (1st Generation)** | Snapdragon 821 | 4GB |
| **Samsung Galaxy Z Flip6** | Snapdragon 8 Gen 3 | 12GB |
| **Samsung Galaxy S21+** | Exynos 2100 | 8GB |
| **Samsung Galaxy S10** | Exynos 9820 | 8GB |
| **Samsung Galaxy A05s** | Snapdragon 680 | 4GB |
| **ZTE Blade A73 5G** | Unisoc T760 | 4GB |
| **Oppo A5 2020** | Snapdragon 665 | 4GB |
| **Nokia 7 plus** | Snapdragon 660 | 4GB |
</details>

<details>
<summary><b>4K60 HEVC SDR - Walk in the Park</b></summary>
<br>
<b>197.3MB 4K 60fps HEVC SDR video compressed using the Medium preset in Compressor.</b>

| Device | Speed (v1.6.3) | Speed (v1.5.2) | Difference |
|---|---|---|---|
| **Google Pixel 8 Pro** | 10s 55ms | 13s 42ms | -2s 87ms (~21% faster) |
| **Google Pixel (1st Generation)** | 24s 49ms | — | New |
| **Samsung Galaxy Z Flip6** | 9s 89ms | — | New |
| **Samsung Galaxy S21+ (Exynos)** | 13s 80ms | 16s 50ms | -2s 70ms (~16% faster) |
| **Samsung Galaxy S10 (Exynos)** | 14s 19ms | 21s 24ms | -7s 05ms (~33% faster) |

The following testing devices were ineligible for this benchmark:
| Device | Reason |
|---|---|
| **Samsung Galaxy A05s** | Hardware cannot handle this video |
| **ZTE Blade A73 5G** | Hardware cannot handle this video |
| **Oppo A5 2020** | Hardware cannot handle this video |
| **Nokia 7 plus** | Hardware cannot handle this video |
</details>

<details>
<summary><b>4K30 HEVC HDR10+ - Challenging Lighting</b></summary>
<br>
<b>136.8MB 4K 30fps HEVC HDR10+ video compressed using the Medium preset in Compressor.</b>

| Device | Speed (v1.6.3) | Speed (v1.5.2) | Difference |
|---|---|---|---|
| **Google Pixel 8 Pro** | 6s 38ms | 7s 23ms | -0s 85ms (~12% faster) |
| **Samsung Galaxy Z Flip6** | 5s 79ms | — | New |
| **Samsung Galaxy S21+ (Exynos)** | 10s 47ms | 9s 27ms | +1s 20ms |
| **Samsung Galaxy S10 (Exynos)** | 11s 25ms | 12s 03ms | -0s 78ms (~6% faster) |
| **ZTE Blade A73 5G** | 24s 40ms | — | New |
| **Oppo A5 2020** | 27s 85ms | — | New |
| **Nokia 7 plus** | 34s 15ms | — | New |

The following testing devices were ineligible for this benchmark:
| Device | Reason |
|---|---|
| **Google Pixel (1st Generation)** | Codec exception (related to handling of HDR) |
| **Samsung Galaxy A05s** | Hardware cannot handle this video |
</details>

<details>
<summary><b>8K24 HEVC SDR - Ultra High Resolution</b></summary>
<br>
<b>266.4MB 8K 24fps HEVC SDR video compressed using the Medium preset in Compressor.</b>

| Device | Speed (v1.6.3) | Speed (v1.5.2) | Difference |
|---|---|---|---|
| **Google Pixel 8 Pro** | 15s 51ms | 16s 21ms | -0s 70ms (~4% faster) |
| **Samsung Galaxy Z Flip6** | 12s 37ms | — | New |
| **Samsung Galaxy S21+ (Exynos)** | 41s 19ms* | 68s 27ms* | -27s 08ms (~40% faster) |
| **Samsung Galaxy S10 (Exynos)** | 21s 61ms | 38s 07ms | -16s 46ms (~43% faster) |

*The Exynos 2100 on the S21+ experiences decoding lag on 8K video playback and compression compared to older and newer SoCs, though performance has notably improved over 1.5.2.

The following testing devices were ineligible for this benchmark:
| Device | Reason |
|---|---|
| **Google Pixel (1st Generation)** | Hardware cannot handle this video |
| **Samsung Galaxy A05s** | Hardware cannot handle this video |
| **ZTE Blade A73 5G** | Hardware cannot handle this video |
| **Oppo A5 2020** | Hardware cannot handle this video |
| **Nokia 7 plus** | Hardware cannot handle this video |
</details>

<details>
<summary><b>1080p60 HEVC SDR - Consistent Subject</b></summary>
<br>
<b>34.5MB 1080p 60fps HEVC SDR video compressed using the Medium preset in Compressor.</b>

| Device | Speed (v1.6.3) | Speed (v1.5.2) | Difference |
|---|---|---|---|
| **Google Pixel 8 Pro** | 4s 45ms | 3s 45ms | +1s 00ms |
| **Google Pixel (1st Generation)** | 7s 45ms | — | New |
| **Samsung Galaxy Z Flip6** | 4s 65ms | — | New |
| **Samsung Galaxy S21+ (Exynos)** | 9s 19ms | 4s 73ms | +4s 46ms |
| **Samsung Galaxy S10 (Exynos)** | 9s 26ms | 5s 38ms | +3s 88ms |
| **Samsung Galaxy A05s** | 19s 09ms | 22s 27ms | -3s 18ms (~14% faster) |
| **ZTE Blade A73 5G** | 7s 25ms | — | New |
| **Oppo A5 2020** | 10s 64ms | — | New |
| **Nokia 7 plus** | 14s 94ms | — | New |
</details>

<details>
<summary><b>Old Benchmarks (v1.5.2)</b></summary>
<br>

| Device | Walk in the Park (4K60) | Challenging Lighting (4K30 HDR) | Ultra High Res (8K24) | Consistent Subject (1080p60) |
|---|---|---|---|---|
| **Google Pixel 8 Pro** | 13s 42ms | 7s 23ms | 16s 21ms | 3s 45ms |
| **Samsung Galaxy S21+ (Exynos)** | 16s 50ms | 9s 27ms | 68s 27ms | 4s 73ms |
| **Samsung Galaxy S10 (Exynos)** | 21s 24ms | 12s 03ms | 38s 07ms | 5s 38ms |
| **Samsung Galaxy S9 (Exynos)** | 35s 77ms | 16s 59ms | Unsupported | 9s 05ms |
| **Samsung Galaxy S7 (Exynos)** | 43s 48ms | Unsupported | Unsupported | 17s 02ms |
| **Samsung Galaxy A71 4G** | 64s 19ms | 22s 84ms | Unsupported | 9s 92ms |
| **Samsung Galaxy A32 4G** | Unsupported | Unsupported | Unsupported | 11s 41ms |
| **Samsung Galaxy A05s** | Unsupported | Unsupported | Unsupported | 22s 27ms |
</details>

<details>
<summary><b>Download Baseline Videos</b></summary>
<br>
If you would like to test Compressor on your own device, you can download the baseline videos used in the benchmarks below. The baseline videos are not for commercial use. Compressor Baseline Videos © 2026 by JoshAtticus is licensed under CC BY-NC-ND 4.0.

You can download them [here](https://l.joshattic.us/mDAc6J)
</details>

<br>

*Why are the benchmarks different between versions?*
Between v1.5.2 and v1.6.3, the testing methodology shifted from keeping phones in a cooler between rounds to realistic ambient room temperature testing (~21°C). Additionally, timing was recorded manually via stopwatch so minor variations are expected. Despite ambient thermal loads, optimizations across newer versions show substantial improvements on heavy workloads (such as 8K & 4K decoding on Exynos chips and budget devices like the A05s).

*Why have some devices changed?*
New devices (Galaxy Z Flip6, ZTE Blade A73 5G, Oppo A5 2020, Nokia 7 plus, and the Google Pixel 1st Gen) were introduced to provide a broader view spanning modern flagships, budget 5G/4G SoCs, and older legacy hardware.

---

## Credits
Compressor wouldn't be possible without these amazing people

[@rA9stuff](https://github.com/rA9stuff) - Inspiration to create Compressor & donated

[@tgranz](https://github.com/tgranz) - Provided funding to get Compressor on Google Play

[@sirtoaks](https://github.com/sirtoaks) - Provided funding to get Compressor on Google Play

[@3r1s-s](https://github.com/3r1s-s) - Created Compressor's new logo

[@AhmedRX20](https://github.com/AhmedRX20) - Translated into Arabic

I would like to acknowledge that Compressor has used AI language models to assist in translation. Should you find any issues in translation, please open a bug report or a pull request so they can be fixed. You can also help translate Compressor on [Crowdin](https://l.joshattic.us/rHkokL)

