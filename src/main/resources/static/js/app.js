const API = '/api';

function escapeHtml(value) {
    return String(value).replace(/[&<>"']/g, c => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    }[c]));
}

function formatINR(amount) {
    return '₹' + Number(amount).toFixed(2);
}

async function apiFetch(path, options = {}) {
    const response = await fetch(API + path, {
        headers: { 'Content-Type': 'application/json' },
        ...options
    });

    let data = null;
    try { data = await response.json(); } catch (e) { /* empty body */ }

    if (!response.ok) {
        const error = new Error((data && data.message) || 'Request failed');
        error.fieldErrors = (data && data.fieldErrors) || null;
        error.status = response.status;
        throw error;
    }
    return data;
}

function showAlert(containerId, type, message) {
    const box = document.getElementById(containerId);
    box.innerHTML =
        `<div class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
         </div>`;
}

function statusBadge(status) {
    const colors = { PENDING: 'warning text-dark', CONFIRMED: 'primary', DELIVERED: 'success' };
    return `<span class="badge bg-${colors[status] || 'secondary'}">${escapeHtml(status)}</span>`;
}