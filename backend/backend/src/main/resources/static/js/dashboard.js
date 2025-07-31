// Wait for DOM to fully load
document.addEventListener("DOMContentLoaded", function () {
  const menuButton = document.getElementById("menu-button");
  const sidebar = document.getElementById("sidebar");

  if (menuButton && sidebar) {
    menuButton.addEventListener("click", function () {
      sidebar.classList.toggle("hidden");
    });

    // Optional: Close sidebar if clicked outside (on mobile)
    document.addEventListener("click", function (event) {
      if (
        !sidebar.contains(event.target) &&
        !menuButton.contains(event.target) &&
        window.innerWidth < 768
      ) {
        sidebar.classList.add("hidden");
      }
    });
  }
});
