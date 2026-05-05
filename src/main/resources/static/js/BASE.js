function toggleSidebar() {
    const sidebar = document.querySelector('aside');
    if (sidebar) {
        sidebar.classList.toggle('mobile-open');
    }
}

// Close sidebar when clicking outside on mobile
document.addEventListener('click', function(event) {
    const sidebar = document.querySelector('aside');
    const btn = document.querySelector('.hamburger-btn');

    if (sidebar && btn && !sidebar.contains(event.target) && !btn.contains(event.target)) {
        sidebar.classList.remove('mobile-open');
    }
});
