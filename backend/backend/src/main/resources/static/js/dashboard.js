//document.addEventListener('DOMContentLoaded', function() {
//  // Mobile menu toggle
//  const menuToggle = document.getElementById('menu-toggle');
//  const sidebar = document.querySelector('.sidebar');
//
//  if (menuToggle && sidebar) {
//    menuToggle.addEventListener('click', function() {
//      sidebar.classList.toggle('active');
//    });
//  }
//
//  // Close sidebar when clicking outside
//  document.addEventListener('click', function(event) {
//    const isClickInsideSidebar = sidebar.contains(event.target);
//    const isClickOnMenuToggle = event.target === menuToggle || menuToggle.contains(event.target);
//
//    if (!isClickInsideSidebar && !isClickOnMenuToggle && sidebar.classList.contains('active')) {
//      sidebar.classList.remove('active');
//    }
//  });
//
//  // Prevent sidebar from closing when clicking inside it
//  sidebar.addEventListener('click', function(event) {
//    event.stopPropagation();
//  });
//
//  // Logout functionality
//  const logoutBtn = document.getElementById('logoutBtn');
//  if (logoutBtn) {
//    logoutBtn.addEventListener('click', async function() {
//      try {
//        const token = localStorage.getItem('jwtToken') ||
//                     document.cookie.split('; ').find(row => row.startsWith('jwtToken='))?.split('=')[1];
//
//        // Call logout API
//        await fetch('/api/auth/logout', {
//          method: 'POST',
//          headers: {
//            'Authorization': `Bearer ${token}`,
//            'Content-Type': 'application/json'
//          },
//          credentials: 'include'
//        });
//
//        // Clear client-side storage
//        localStorage.removeItem('jwtToken');
//        document.cookie = 'jwtToken=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
//
//        // Redirect to home page
//        window.location.href = '/';
//
//      } catch (error) {
//        console.error('Logout error:', error);
//        // Fallback: clear storage and redirect
//        localStorage.removeItem('jwtToken');
//        document.cookie = 'jwtToken=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT';
//        window.location.href = '/';
//      }
//    });
//  }
//
//  // Check authentication on page load
//  const token = localStorage.getItem('jwtToken') ||
//               document.cookie.split('; ').find(row => row.startsWith('jwtToken='))?.split('=')[1];
//
//  if (!token && window.location.pathname !== '/') {
//    window.location.href = '/';
//  }
//
//  // Load dashboard data if authenticated
//  if (token) {
//    simulateDataLoading();
//  }
//});
//
//function simulateDataLoading() {
//  // Your existing data loading logic
//  console.log('Loading dashboard data...');
//}